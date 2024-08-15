# coding=utf-8
# weak_pwd
# 2024/6/18 14:34

from impacket.examples.utils import parse_target
from impacket.smbconnection import SMBConnection
import pymysql


def mysql_login(user, password, port):
    # MySQL 服务器连接信息
    host = '127.0.0.1'
    try:
        # 创建数据库连接
        connection = pymysql.connect(
            host=host,
            port=port,
            user=user,
            password=password,
        )

        # 检查连接是否成功
        with connection.cursor() as cursor:
            cursor.execute("SELECT VERSION()")
            version = cursor.fetchone()
            return True

    except pymysql.MySQLError as e:
        return False

    finally:
        # 关闭连接
        if 'connection' in locals():
            connection.close()


def smb_login(username, password):
    domain = ''
    address = '127.0.0.1'
    target_ip = address
    domain = ''
    lmhash = ''
    nthash = ''
    try:
        smbClient = SMBConnection(address, target_ip, sess_port=int(445))
        smbClient.login(username, password, domain, lmhash, nthash)
        return True
    except Exception as e:
        return False

