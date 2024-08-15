package com.tpp.threat_perception_platform.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 操作日志记录
 * @TableName oper_log
 */
@Data
public class OperLog implements Serializable {
    /**
     * 日志主键
     */
    private Long id;

    /**
     * 模块标题
     */
    private String title;

    /**
     * 日志内容
     */
    private String content;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 操作人员
     */
    private String operName;

    /**
     * 请求URL
     */
    private String requestUrl;

    /**
     * 请求IP地址
     */
    private String ip;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 方法响应参数
     */
    private String responseResult;

    /**
     * 操作状态（0正常 1异常）
     */
    private Integer status;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 操作时间
     */
    private Date operTime;

    /**
     * 方法执行耗时（单位：毫秒）
     */
    private Long takeTime;

    private static final long serialVersionUID = 1L;
}