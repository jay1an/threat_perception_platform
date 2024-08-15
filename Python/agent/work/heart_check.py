# coding=utf-8
# heart_check
# 2024/6/11 10:12
# 每三秒发送一次心跳包, 心跳包发送到指定队列（status_queue）
# 心跳包内容包含mac地址
# 使用线程作为心跳包发送器
import json
import threading
import time


class HeartCheck(threading.Thread):
    def __init__(self, mq, mac):
        threading.Thread.__init__(self)
        self.mq = mq  # mq对象
        self.mac = mac

    def run(self):
        """
        心跳包发送器
        :return:
        """
        while True:
            heart_data = {"mac": self.mac, "status": 1}
            heart_data = json.dumps(heart_data)
            self.mq.produce_heartbeat(heart_data)
            time.sleep(3)
