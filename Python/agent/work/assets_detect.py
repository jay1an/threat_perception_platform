# coding=utf-8
# assets_detect
# 2024/6/11 16:54
import sys
import threading

sys.coinit_flags = 0  # 避免报错。。。I dont know why

import winreg
import wmi
import pythoncom
import json
import nmap


class AssetsDetect(threading.Thread):
    """资产专用类"""

    def __init__(self, data, mq):
        super().__init__()
        self.__hostname = data['hostname']
        self.__mac = data['mac']
        self.__account = data['account']
        self.__process = data['process']
        self.__service = data['service']
        self.__app = data['app']
        self.__mq = mq

    def __account_detect(self):
        """
        探测账号
        :return:
        """
        try:
            # 创建wmi客户端对象
            pythoncom.CoInitialize()
            c = wmi.WMI()

            account_list = []
            # 获取所有用户
            for user in c.Win32_UserAccount():
                user_dict = {
                    "mac": self.__mac,
                    "account_type": user.AccountType,
                    "caption": user.Caption,
                    "domain": user.Domain,
                    "local_account": user.LocalAccount,
                    "description": user.Description,
                    "name": user.Name,
                    "full_name": user.FullName,
                    "sid": user.SID,
                    "sid_type": user.SIDType,
                    "status": user.Status,
                    "disabled": user.Disabled,
                    "lockout": user.Lockout,
                    "password_changeable": user.PasswordChangeable,
                    "password_expires": user.PasswordExpires,
                    "password_required": user.PasswordRequired,
                }
                account_list.append(user_dict)

            pythoncom.CoUninitialize()

            # 转换成JSON字符串
            account_data = json.dumps(account_list)

            # 发送给队列
            self.__mq.produce_account_data(account_data)

        except Exception as e:
            print(f"探测账号数据发生异常：{str(e)}")

    def __service_detect(self):
        """
        探测服务
        :return:
        """
        nm = nmap.PortScanner()
        nm.scan(hosts='127.0.0.1', arguments='-sTV')

        state = nm.all_hosts()
        res_list = []
        if state:
            for host in nm.all_hosts():
                for proto in nm[host].all_protocols():
                    lport = nm[host][proto].keys()
                    for port in lport:
                        res_dict = {
                            "mac": self.__mac,
                            "protocol": proto,
                            "port": port,
                            "state": nm[host][proto][port]['state'],
                            "name": nm[host][proto][port]['name'],
                            "product": nm[host][proto][port]['product'],
                            "version": nm[host][proto][port]['version'],
                            'extrainfo': nm[host][proto][port]['extrainfo'],
                        }
                        res_list.append(res_dict)
        res_json = json.dumps(res_list)
        self.__mq.produce_service_data(res_json)

    def __process_detect(self):
        """
        探测进程
        :return:
        """
        pythoncom.CoInitialize()
        c = wmi.WMI()
        process_list = []
        for process in c.Win32_Process():
            process_dict = {
                "mac": self.__mac,
                "name": process.Name,
                "pid": process.ProcessId,
                "ppid": process.ParentProcessId,
                "cmd": process.CommandLine,
                'description': process.Description,
                'priority': process.Priority
            }
            process_list.append(process_dict)
        pythoncom.CoUninitialize()
        process_data = json.dumps(process_list)
        # print(process_data)
        self.__mq.produce_process_data(process_data)

    def __app_detect(self):
        """
        探测应用
        :return:
        """
        registry_key = winreg.OpenKey(winreg.HKEY_LOCAL_MACHINE, r'SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall')
        software_list = []
        number = winreg.QueryInfoKey(registry_key)[0]
        for i in range(number):
            try:
                sub_key_name = winreg.EnumKey(registry_key, i)
                sub_key = winreg.OpenKey(registry_key, sub_key_name)
                software = {}
                try:
                    software['mac'] = self.__mac
                    software['display_name'] = winreg.QueryValueEx(sub_key, 'DisplayName')[0]
                    software['install_location'] = winreg.QueryValueEx(sub_key, 'InstallLocation')[0]
                    software['uninstall_string'] = winreg.QueryValueEx(sub_key, 'UninstallString')[0]
                    software_list.append(software)
                except WindowsError:
                    continue
            except WindowsError:
                print("无法打开注册表键")
                break
        app_data = json.dumps(software_list)
        self.__mq.produce_app_data(app_data)

    def detect(self):
        """
        探测
        :return:
        """
        # 根据指令决定要执行的探测
        if self.__account == 1:
            print("开始探测账号数据....")
            self.__account_detect()
            print("账号数据探测完成...")
        if self.__process == 1:
            print("开始探测进程数据....")
            self.__process_detect()
            print("进程数据探测完成...")
        if self.__service == 1:
            print("开始探测服务数据....")
            self.__service_detect()
            print("服务数据探测完成...")
        if self.__app == 1:
            print("开始探测应用数据....")
            self.__app_detect()
            print("应用数据探测完成...")

    def run(self):
        try:
            self.detect()
        finally:
            self.__mq.close()
