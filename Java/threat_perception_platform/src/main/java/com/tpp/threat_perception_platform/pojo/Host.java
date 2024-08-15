package com.tpp.threat_perception_platform.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName host
 */
@Data
public class Host implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 主机名
     */
    private String hostname;

    /**
     * ip地址
     */
    private String ip;

    /**
     * MAC地址
     */
    private String mac;

    /**
     * 操作系统名称
     */
    private String osName;

    /**
     * 操作系统版本
     */
    private String osVersion;

    /**
     * 操作系统类型
     */
    private String osType;

    /**
     * 操作系统位数
     */
    private String osArch;

    /**
     * cpu名称
     */
    private String cpu;

    /**
     * 内存
     */
    private String ram;

    /**
     * 状态0,1  1为上线
     */
    private Integer status;

    /**
     * 0为不开启同步日志，1为开启同步日志
     */
    private Integer SyncLogs;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}