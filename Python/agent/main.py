# coding=utf-8
# main
# 2024/6/6 10:58
from time import sleep

from system.system_info import SystemInfo
from rabbitmq.rabbitmq import RabbitMQ
from work.heart_check import HeartCheck
from work.instruction_consumer import InstructionConsumer

if __name__ == '__main__':
    # 先获取系统信息
    print("开始获取系统信息..")
    systemInfo = SystemInfo()
    data = systemInfo.get_system_info()
    print("获取完毕..")
    print(data)
    print("=====================")
    print("开始发送系统消息到平台端..")
    rabbitmq_send = RabbitMQ()
    rabbitmq_send.produce_sysinfo(data)
    print("发送完毕..")
    print("=====================")
    #     发送心跳的线程
    print("开始每三秒发送一次发送心跳消息到平台端...")
    heartCheck = HeartCheck(rabbitmq_send, systemInfo.mac)
    heartCheck.start()
    # 消费者线程
    print("=====================")
    sleep(2)
    print("开始监听平台端指令消息...")
    # mq规定，接收和发送不能用同一个mq对象
    rabbitmq_receive = RabbitMQ()
    instruction_consumer = InstructionConsumer(rabbitmq_receive, systemInfo.mac)
    instruction_consumer.start()
    print("=====================")
    # 后期可以加上一个下线就发送一个status=0的消息