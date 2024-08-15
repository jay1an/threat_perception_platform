package com.tpp.threat_perception_platform.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName host_baseline
 */
@Data
public class HostBaseline implements Serializable {
    /**
     * 自增id
     */
    private Integer id;

    /**
     * 唯一标识主机
     */
    private String mac;

    /**
     * 策略类型，例如SysAccountPolicy...
     */
    private String policyType;

    /**
     * 策略名称，例如MinmumPasswordAge
     */
    private String policyName;

    /**
     * 0为异常，1为正常
     */
    private Integer resultType;

    /**
     * 实际值
     */
    private String actualValue;

    /**
     * 期望值
     */
    private String expectedValue;

    /**
     * 日志内容
     */
    private String message;

    /**
     * 日志记录时间
     */
    private Date timestamp;

    private static final long serialVersionUID = 1L;
}