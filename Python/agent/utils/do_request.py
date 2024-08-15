# coding=utf-8
# do_request
# 2024/6/17 14:40

import requests


class DoRequest:
    def __init__(self, path, method, data):
        # 请求路径
        self.__path = path
        # 请求方法
        self.__method = method
        # 载荷
        self.__data = data

    def __do_get(self):
        """
        发送get请求
        :return:
        """
        # 准备请求地址
        url = self.__path + self.__data
        response = requests.get(url)
        result = response.content.decode('utf-8')
        return result

    def __do_post(self):
        """
        发送post请求
        :return:
        """
        # 准备请求地址
        url = self.__path
        response = requests.post(url=url, data=self.__data)
        result = response.content.decode('utf-8')
        return result

    def do_request(self):
        """
        发送请求
        :return:
        """
        if self.__method == 'GET':
            return self.__do_get()
        elif self.__method == 'POST':
            return self.__do_post()
        else:
            return None
