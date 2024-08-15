# coding=utf-8
# baseline_check
# 2024/6/21 19:32
import json
import os
import subprocess
import threading
import re


class BaselineCheck(threading.Thread):

    def __init__(self, mac, mq):
        threading.Thread.__init__(self)
        self.__mac = mac
        self.__mq = mq
        self.__powershell_script_path = './powershell/Windows.ps1'
        self.__log_path = './baseline.log'

    def __parse_log_line(self, line):
        """ 解析日志行并返回提取的字段 """
        # 正则表达式匹配模式
        pattern = r'^([^\s:]+)::([^\s]+)\s+\[(.*?)\]\|([^|]*)\|([^|]*)\|(.*)$'
        match = re.match(pattern, line)
        if match:
            policy_type = match.group(1).strip()
            policy_name = match.group(2).strip()
            result_type = match.group(3).strip()
            actual_value = match.group(4).strip()
            expected_value = match.group(5).strip()
            message = match.group(6).strip()
            return {
                'policy_type': policy_type,
                'policy_name': policy_name,
                'result_type': 1 if result_type == '合格项' else 0,
                'actual_value': actual_value,
                'expected_value': expected_value,
                'message': message
            }
        else:
            # 处理特殊情况
            match = re.match(r'^(.*?)\s+\[(.*?)\]\-(.*?)$', line)
            if match:
                policy_type = match.group(1).strip()
                result_type = match.group(2).strip()
                message = match.group(3).strip()
                return {
                    'policy_type': policy_type,
                    'policy_name': '',
                    'result_type': 1 if result_type == '合格项' else 0,
                    'actual_value': '',
                    'expected_value': '',
                    'message': message
                }
        print("无法解析日志行：", line)

    def __baseline_check(self):
        # 进行检测
        subprocess.run(['powershell.exe', '-File', self.__powershell_script_path], capture_output=True, text=True,
                       check=True)
        # 处理文件内容
        count = 0
        baseline_check_res = []
        with open(self.__log_path, 'r', encoding='utf-8') as f:
            for line in f.readlines():
                line = line.strip()
                if "合格项" in line or "异常项" in line:
                    line = self.__parse_log_line(line)
                    line['mac'] = self.__mac
                    print(line)
                    count += 1
                    baseline_check_res.append(line)
        print(f"检测结果：{count}条")
        # 删除文件
        if os.path.exists(self.__log_path):
            os.remove(self.__log_path)
        # 发送消息
        data = json.dumps(baseline_check_res)
        self.__mq.produce_baseline_data(data)

    def run(self):
        try:
            print("开始基线检查....")
            self.__baseline_check()
        finally:
            print("基线检查结束....")
            self.__mq.close()
