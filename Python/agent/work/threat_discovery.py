# coding=utf-8
# threat_detect
# 2024/6/14 11:12

# coding=utf-8
# assets_detect
# 2024/6/11 16:54
import sys

from agent.utils.weak_pwd import mysql_login, smb_login
from agent.utils.do_request import DoRequest

sys.coinit_flags = 0  # 避免报错。。。I dont know why

import wmi
import pythoncom
import json
import threading


class ThreatDiscovery(threading.Thread):
    """威胁发现类"""

    def __init__(self, data, mq):
        super().__init__()
        self.__data = data
        self.__mq = mq

    def __hotfix_discovery(self):
        """
        补丁检测
        """
        pythoncom.CoInitialize()
        # 创建WMI客户端
        c = wmi.WMI()
        # 获取补丁信息
        hotfixes = c.query("SELECT HotFixID FROM Win32_QuickFixEngineering")
        print("扫描出的补丁：")
        # 组装补丁ID
        hotfix_list = []
        for hotfix in hotfixes:
            data = {
                'mac': self.__data['mac'],
                'hotfix_id': hotfix.HotFixID
            }
            print(hotfix.HotFixID)
            hotfix_list.append(data)
        # 去初始化
        pythoncom.CoUninitialize()
        # 转换成JSON数据
        data = json.dumps(hotfix_list)

        # 发送到队列
        self.__mq.produce_hotfix_data(data)

    def __vulnerability_discovery(self):
        """
        漏洞检测
        """
        val_list = []
        # 获取漏洞库
        vul_dbs = self.__data['vulnerabilities']
        for vul_db in vul_dbs:
            method = vul_db['requestType']
            path = vul_db['path']
            data = vul_db['payload']
            flag = vul_db['flag']
            path = f"http://localhost{path}"
            request = DoRequest(path, method, data)
            res = request.do_request()  # 发送请求
            # 根据请求结果判断是否有漏洞
            if flag in res:
                # 有漏洞
                val = {
                    'mac': self.__data['mac'],
                    'vulnerability_id': vul_db['id']
                }
                val_list.append(val)
        if len(val_list) > 0:
            data = json.dumps(val_list)
            print("检测出漏洞 ==> " + data)
        else:
            data = self.__data['mac']
            print("没有检测出漏洞")
        self.__mq.produce_vulnerability_data(data)

    def __system_weak_pwd_discovery(self, res_list):
        """
        系统弱口令检测
        """
        print("正在进行系统弱口令检测...")
        weak_pwds = self.__data['weakPwds']
        usernames = self.__data['usernames']
        print(usernames)
        filter_usernmaes = ["Administrator", "DefaultAccount", "Guest", "WDAGUtilityAccount", "__HSKDDNS_USER__"]
        for username in usernames:
            if username not in filter_usernmaes:
                for weak_pwd in weak_pwds:
                    if smb_login(username, weak_pwd):
                        val = {
                            'mac': self.__data['mac'],
                            'weak_pwd': weak_pwd,
                            'username': username,
                            'type': "windows"
                        }
                        print("系统账号弱密码！！ ==> 账号: " + username + " 密码: " + weak_pwd)
                        res_list.append(val)
        print("系统弱口令检测结束...")

    def __service_weak_pwd_discovery(self, res_list):
        print("正在进行服务弱口令检测...")
        weak_pwds = self.__data['weakPwds']
        service_dict = self.__data['weakPwdServiceMap']
        print("要进行弱密码检测的服务有:" + str(service_dict))
        username = 'root'
        for service_name in service_dict:
            for port in service_dict[service_name]:
                port = int(port)
                for weak_pwd in weak_pwds:
                    if mysql_login(username, weak_pwd, port):
                        val = {
                            'mac': self.__data['mac'],
                            'weak_pwd': weak_pwd,
                            'username': username,
                            'type': service_name
                        }
                        print(f"{service_name}服务弱密码！！ ==> 账号: " + username + " 密码: " + weak_pwd)
                        res_list.append(val)
        print("服务弱口令检测结束...")

    def __weakPwd_discovery(self):
        val_list = []
        self.__system_weak_pwd_discovery(val_list)  # 系统弱口令
        self.__service_weak_pwd_discovery(val_list)  # 服务弱口令
        if len(val_list) > 0:
            data = json.dumps(val_list)
        else:
            data = self.__data['mac'];
            print("无弱密码！！！")
        self.__mq.produce_weak_pwd_data(data)

    def __app_threats_discovery(self):
        """
        应用威胁探测
        :return:
        """
        val_list = []
        service_dict = self.__data['appThreatsServiceMap']
        app_threats_dict = self.__data['appThreatsDbMap']
        print("要探测的服务有：" + str(service_dict))
        for service_name in service_dict:
            for port in service_dict[service_name]:
                for app_threat in app_threats_dict[service_name]:
                    code_str = app_threat['pocCode']
                    globals_dict = {}
                    globals_dict['host'] = 'localhost'
                    globals_dict['port'] = port
                    exec(code_str, globals_dict)
                    # 可以在这里处理执行结果
                    result = globals_dict.get('result', None)  # 获取exec中result的变量
                    print("应用风险检测结果:" + str(result))
                    if result and result[0] == 1:
                        val = {
                            'mac': self.__data['mac'],
                            'app_threat_id': app_threat['id'],
                        }
                        val_list.append(val)

        if len(val_list) > 0:
            data = json.dumps(val_list)
            print("存在应用威胁！！ ==> " + data)
        else:
            print("无应用威胁！！")
            data = self.__data['mac']
        self.__mq.produce_app_threats_data(data)

    def detect(self):
        """
        探测
        :return:
        """
        # 根据指令决定要执行的探测
        if self.__data['hotfix'] == 1:
            print("危险探测项 - 补丁正在探测中....")
            self.__hotfix_discovery()
            print("危险探测项 - 补丁探测结束...")
        if self.__data['vulnerability'] == 1:
            print("危险探测项 - 漏洞正在探测中....")
            self.__vulnerability_discovery()
            print("危险探测项 - 漏洞探测结束...")
        if self.__data['weakPwd'] == 1:
            print("危险探测项 - 弱口令正在探测中....")
            self.__weakPwd_discovery()
            print("危险探测项 - 弱口令探测结束...")
        if self.__data['app'] == 1:
            print("危险探测项 - 应用威胁正在探测中....")
            self.__app_threats_discovery()
            print("危险探测项 - 应用威胁探测结束...")

    def run(self):
        try:
            self.detect()
        finally:
            self.__mq.close()
