package com.tpp.threat_perception_platform.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName app
 */
@Data
public class App implements Serializable {
    /**
     * id，主键
     */
    private Integer id;

    /**
     * mac地址
     */
    private String mac;

    /**
     * 应用展示名
     */
    private String displayName;

    /**
     * 安装路径
     */
    private String installLocation;

    /**
     * 卸载程序位置
     */
    private String uninstallString;

    private static final long serialVersionUID = 1L;
}