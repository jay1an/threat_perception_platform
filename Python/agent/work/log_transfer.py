# coding=utf-8
# log_transfer
# 2024/6/20 15:10

import json
import threading
from datetime import datetime

from evtx import PyEvtxParser
import re
import html
from xml.dom import minidom


class LogTransfer(threading.Thread):

    def __init__(self, mq, mac):
        super().__init__()
        self.__mq = mq
        self.__mac = mac
        self.__security_log_path = r'C:\Windows\system32\winevt\Logs\Security.evtx'
        self.__system_log_path = r'C:\Windows\system32\winevt\Logs\System.evtx'

    def __get_log_info(self, event_path, event_id_to_filter=None):
        """
        过滤自己想要的日志
        :param self: 日志的路径
        :param event_id_to_filter: 过滤的日志ID, 如果为None，则返回所有日志
        :return:
        """
        # 创建一个EvtxParser对象来读取文件
        parser = PyEvtxParser(event_path)
        pattern = re.compile(r'<EventID.*?(\d+)</EventID>')
        # 给一个容器，装最终的事件
        event_list = []
        # 遍历日志条目，筛选出特定事件ID的日志
        for record in parser.records():
            xml_data = record['data']
            res = re.findall(pattern, xml_data)
            event_id = int(res[0])
            if event_id_to_filter is None or event_id in event_id_to_filter:
                # 将字符串解析为 datetime 对象
                timestamp = datetime.strptime(record['timestamp'], "%Y-%m-%d %H:%M:%S.%f %Z")
                # 计算对应的毫秒级时间戳
                timestamp_ms = int(datetime.timestamp(timestamp) * 1000) + 8 * 3600 * 1000 # 将时区转换为中国标准时间
                r = {'event_id': event_id, 'timestamp': timestamp_ms, 'mac': self.__mac}
                xml_doc = minidom.parseString(xml_data)
                data = xml_doc.getElementsByTagName('Data')
                dict = {}
                for d in data:
                    try:
                        name = d.getAttribute('Name')
                        value = html.unescape(d.childNodes[0].data)
                        dict[name] = value
                    except Exception as e:
                        pass
                r['data'] = dict
                event_list.append(r)
        return event_list

    def __get_account_log(self):
        """
        获取账号日志
        :return:
        """
        # 4624:登录成功 4625:登陆失败 4720:用户被创建 4726:用户被删除 4723:更改密码
        event_id_to_filter = [4624, 4625, 4720, 4726, 4726, 4723]
        account_log = self.__get_log_info(self.__security_log_path, event_id_to_filter)
        return account_log

    def __get_system_log(self):
        event_id_to_filter = [7040, 7045, 1102, 7036]
        system_log = self.__get_log_info(self.__system_log_path, event_id_to_filter)
        return system_log

    def get_all_log(self):
        account_log = self.__get_account_log()
        system_log = self.__get_system_log()
        return account_log, system_log

    def run(self):
        try:
            print("开始日志收集上传.....")
            account_log, system_log = self.get_all_log()
            all_log = account_log + system_log
            # for i in system_log:
            #     print(i)
            data = json.dumps(all_log)
            print("扫描出:账号日志:{}条,系统日志:{}条".format(len(account_log), len(system_log)))
            self.__mq.produce_log_data(data)
        finally:
            self.__mq.close()
            print("日志收集上传完成.....")
