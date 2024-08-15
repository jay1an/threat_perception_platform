package com.tpp.threat_perception_platform.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 
 * @TableName hotfix
 */
@Data
public class Hotfix implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * mac地址，标识唯一主机
     */
    private String mac;

    /**
     * 补丁id
     */
    private String hotfixId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 可能的漏洞
     */
    private List<String> cveList;


    private static final long serialVersionUID = 1L;
}