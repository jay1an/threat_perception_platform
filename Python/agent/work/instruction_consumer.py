# coding=utf-8
# consumer
# 2024/6/11 15:57
import threading


class InstructionConsumer(threading.Thread):
    """消费者，消费平台端传来的消息"""

    def __init__(self, mq, mac):
        threading.Thread.__init__(self)
        self.mq = mq
        self.mac = mac
        self.daemon = True

    def run(self):
        queue_name = "agent_" + self.mac.replace(":", "") + "_queue"
        self.mq.instruction_consume(queue_name)
