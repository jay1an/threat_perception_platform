package com.tpp.threat_perception_platform.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName host_logs
 */
@Data
public class HostLogs implements Serializable {
    /**
     * 自增id，主键
     */
    private Integer id;

    /**
     * 标识事件
     */
    private Integer eventId;

    /**
     * 唯一标识主机
     */
    private String mac;

    /**
     * 时间
     */
    private Date timestamp;

    /**
     * 日志详细内容
     */
    private String data;

    private static final long serialVersionUID = 1L;
}