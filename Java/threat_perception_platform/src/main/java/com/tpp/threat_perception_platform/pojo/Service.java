package com.tpp.threat_perception_platform.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName service
 */
@Data
public class Service implements Serializable {
    /**
     * id，主键
     */
    private Integer id;

    /**
     * mac地址
     */
    private String mac;

    /**
     * 协议
     */
    private String protocol;

    /**
     * 状态open或者close
     */
    private String state;

    /**
     * 服务名称
     */
    private String name;

    /**
     * 服务产品名称
     */
    private String product;

    /**
     * 版本
     */
    private String version;

    /**
     * 额外信息
     */
    private String extrainfo;

    /**
     * 端口号
     */
    private Integer port;

    private static final long serialVersionUID = 1L;
}