# coding=utf-8
# system_info
# 2024/6/6 19:19

import socket
import uuid
import subprocess
import platform
import math
import psutil
import json


def get_win_os_name():
    process = subprocess.Popen(['wmic', 'os', 'get', 'Caption'], stdout=subprocess.PIPE)
    output, _ = process.communicate()
    # 解析输出结果，获取操作系统名称
    return output.strip().decode('GBK').split('\n')[1]


def get_win_cpu_name():
    process = subprocess.Popen(['wmic', 'cpu', 'get', 'name'], stdout=subprocess.PIPE)
    output, _ = process.communicate()
    return output.strip().decode('utf-8').split('\n')[1]


class SystemInfo:
    def __init__(self):
        self.__hostname = ''
        self.__ip = ''
        self.__mac = ''

        self.__os_name = ''
        self.__os_version = ''
        self.__os_type = ''
        self.__os_arch = ''

        self.__cpu = ''
        self.__ram = ''

    def __get_hostname(self):
        self.__hostname = socket.gethostname()

    def __get_ip(self):
        s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        s.connect(("8.8.8.8", 80))
        self.__ip = s.getsockname()[0]
        s.close()

    def __get_mac(self):
        mac = ':'.join(("%012X" % uuid.getnode())[i:i + 2] for i in range(0, 12, 2))
        self.__mac = mac

    def __get_os_info(self):
        self.__os_type = platform.system()
        self.__os_name = get_win_os_name()
        self.__os_version = platform.version()
        self.__os_arch = platform.architecture()[0]

    def __get_cpu_info(self):
        self.__cpu = get_win_cpu_name()

    def __get_ram(self):
        self.__ram = "%dGB" % math.ceil(psutil.virtual_memory().total / 1024 / 1024 / 1024)

    @property
    def mac(self):
        return self.__mac

    def get_system_info(self):
        """
        获取系统信息
        :return: Json形式的数据
        """
        self.__get_hostname()
        self.__get_ip()
        self.__get_mac()
        self.__get_os_info()
        self.__get_cpu_info()
        self.__get_ram()
        info = {
            'hostname': self.__hostname,
            'ip': self.__ip,
            'mac': self.__mac,
            'os_name': self.__os_name,
            'os_version': self.__os_version,
            'os_type': self.__os_type,
            'os_arch': self.__os_arch,
            'cpu': self.__cpu,
            'ram': self.__ram,
        }
        return json.dumps(info)
