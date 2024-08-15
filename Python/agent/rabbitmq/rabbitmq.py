# coding=utf-8
# rabbitmq
# 2024/6/6 20:05
import json
import pika
from agent.work.assets_detect import AssetsDetect
from agent.work.threat_discovery import ThreatDiscovery
from agent.work.log_transfer import LogTransfer
from agent.work.baseline_check import BaselineCheck
from datetime import datetime


class RabbitMQ:
    def __init__(self):
        self.__host = ''  # RabbitMQ服务器地址
        self.__port = 0  # RabbitMQ服务器端口
        self.__username = ''  # RabbitMQ服务器用户名
        self.__password = ''  # RabbitMQ服务器密码
        self.__virtual_host = ''  # 虚拟主机
        self.__connection = None
        self.__channel = None
        # 初始化时就连接
        self.__connect()

    def __connect(self):
        # 认证对象
        credentials = pika.PlainCredentials(self.__username, self.__password)
        # 生成参数
        parameters = pika.ConnectionParameters(
            host=self.__host,
            port=self.__port,
            virtual_host=self.__virtual_host,
            credentials=credentials
        )
        # 获取链接
        self.__connection = pika.BlockingConnection(parameters)
        # 通道
        self.__channel = self.__connection.channel()

    def close(self):
        if self.__connection and not self.__connection.is_closed:
            self.__connection.close()

    def __my__producer(self, exchange, routing_key, data):
        self.__channel.basic_publish(exchange=exchange, routing_key=routing_key, body=data)

    # 回调函数
    def __process_message(self, ch, method, properties, message):
        """ 处理消息，根据消息type不同有不同的处理方法 """
        data = json.loads(message)
        start_time = datetime.now().strftime('%Y-%m-%d %H:%M:%S')
        print(f"===================== {start_time} ======================")
        print("收到消息:")
        print(data)
        mq = RabbitMQ()
        # 根据data中的标记不同，创建不同的线程进行工作
        if data['type'] == 'assets':
            mq = RabbitMQ()
            AssetsDetect(data, mq).start()
        if data['type'] == 'threats':
            mq = RabbitMQ()
            ThreatDiscovery(data, mq).start()
        if data['type'] == 'logs':
            mq = RabbitMQ()
            LogTransfer(mq, data['mac']).start()
        if data['type'] == 'baseline':
            mq = RabbitMQ()
            BaselineCheck(data['mac'], mq).start()

    def instruction_consume(self, queue_name):
        """消费消息，需要传入队列名"""
        self.__channel.basic_consume(queue=queue_name, on_message_callback=self.__process_message,
                                     auto_ack=True)  # 自动确认
        self.__channel.start_consuming()

    def produce_sysinfo(self, data):
        """发送sysInfo到特定队列"""
        exchange = 'sysinfo_exchange'
        routing_key = 'sysinfo'
        self.__my__producer(exchange, routing_key, data)

    def produce_heartbeat(self, data):
        """发送heartbeat到特定队列"""
        exchange = 'sysinfo_exchange'
        routing_key = 'status'
        self.__my__producer(exchange, routing_key, data)

    # ===============================
    #
    # 下面这四个是用于资产探测的消息生产方法
    #
    # ===============================
    def produce_account_data(self, data):
        """发送账号探测信息到特定队列"""
        exchange = 'sysinfo_exchange'
        routing_key = 'account'
        self.__my__producer(exchange, routing_key, data)

    def produce_service_data(self, data):
        """发送服务探测信息到特定队列"""
        exchange = 'sysinfo_exchange'
        routing_key = 'service'
        self.__my__producer(exchange, routing_key, data)

    def produce_process_data(self, data):
        """发送进程探测信息到特定队列"""
        exchange = 'sysinfo_exchange'
        routing_key = 'process'
        self.__my__producer(exchange, routing_key, data)

    def produce_app_data(self, data):
        """发送应用探测信息到特定队列"""
        exchange = 'sysinfo_exchange'
        routing_key = 'app'
        self.__my__producer(exchange, routing_key, data)

    # ===============================
    #
    # 下面是用于危险发现的消息生产方法
    #
    # ===============================

    def produce_hotfix_data(self, data):
        """发送补丁发现信息到特定队列"""
        exchange = 'sysinfo_exchange'
        routing_key = 'hotfix'
        self.__my__producer(exchange, routing_key, data)

    def produce_vulnerability_data(self, data):
        """发送漏洞发现信息到特定队列"""
        exchange = 'sysinfo_exchange'
        routing_key = 'vulnerability'
        self.__my__producer(exchange, routing_key, data)

    def produce_weak_pwd_data(self, data):
        """发送弱密码发现信息到特定队列"""
        exchange = 'sysinfo_exchange'
        routing_key = 'weakPwd'
        self.__my__producer(exchange, routing_key, data)

    def produce_app_threats_data(self, data):
        """发送应用威胁发现信息到特定队列"""
        exchange = 'sysinfo_exchange'
        routing_key = 'app_threats'
        self.__my__producer(exchange, routing_key, data)

    def produce_log_data(self, data):
        """发送日志信息到特定队列"""
        exchange = 'sysinfo_exchange'
        routing_key = 'logs'
        self.__my__producer(exchange, routing_key, data)

    # ====================================
    def produce_baseline_data(self, data):
        """发送基线检查信息到特定队列"""
        exchange = 'sysinfo_exchange'
        routing_key = 'baseline'
        self.__my__producer(exchange, routing_key, data)
