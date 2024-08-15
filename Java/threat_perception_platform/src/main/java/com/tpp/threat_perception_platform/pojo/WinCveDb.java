package com.tpp.threat_perception_platform.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * Windows漏洞与KB补丁关系库
 * @TableName win_cve_db
 */
@Data
public class WinCveDb implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 数据日期
     */
    private String dt;

    /**
     * CVE id
     */
    private String cve;

    /**
     * 各产品的CVSS Score平均值
     */
    private String score;

    /**
     * 漏洞影响范围，以productid集表示
     */
    private String productIdList;

    /**
     * 漏洞对应补丁号合集
     */
    private String kbList;

    /**
     * cvrf id
     */
    private String cvrfId;

    private static final long serialVersionUID = 1L;
}