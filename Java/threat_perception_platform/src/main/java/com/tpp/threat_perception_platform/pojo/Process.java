package com.tpp.threat_perception_platform.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName process
 */
@Data
public class Process implements Serializable {
    /**
     * id主键
     */
    private Integer id;

    /**
     * mac地址
     */
    private String mac;

    /**
     * 进程名
     */
    private String name;

    /**
     * pid
     */
    private Integer pid;

    /**
     * 父进程pid
     */
    private Integer ppid;

    /**
     * 表示该进程的完整命令行字符串
     */
    private String cmd;

    /**
     * 描述
     */
    private String description;

    /**
     * 优先级
     */
    private Integer priority;

    private static final long serialVersionUID = 1L;
}