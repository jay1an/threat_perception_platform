package com.tpp.threat_perception_platform.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName base_line_task
 */
@Data
public class BaseLineTask implements Serializable {
    /**
     * 自增id，主键
     */
    private Integer id;

    /**
     * 任务描述
     */
    private String name;

    /**
     * 任务定时时间
     */
    private Date scheduledTime;

    /**
     * 任务是否完成，0为待完成
     */
    private Integer status;

    /**
     * 记录任务涉及的主机
     */
    private String hosts;

    private static final long serialVersionUID = 1L;
}