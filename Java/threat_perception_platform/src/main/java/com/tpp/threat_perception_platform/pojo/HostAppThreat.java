package com.tpp.threat_perception_platform.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName host_app_threat
 */
@Data
public class HostAppThreat implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 唯一标识主机
     */
    private String mac;

    /**
     * 唯一标识应用风险
     */
    private Integer appThreatId;

    /**
     * 检测时间
     */
    private Date createTime;

    /**
     * 对应的应用威胁
     */
    private AppThreatsDb appThreatsDb;


    private static final long serialVersionUID = 1L;
}