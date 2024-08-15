package com.tpp.threat_perception_platform.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 
 * @TableName host_weak_pwd
 */
@Data
public class HostWeakPwd implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 标识唯一主机
     */
    private String mac;

    /**
     * 账号
     */
    private String username;

    /**
     * 弱密码
     */
    private String weakPwd;

    /**
     * 账号类型
     */
    private String type;

    /**
     * 检测时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
}