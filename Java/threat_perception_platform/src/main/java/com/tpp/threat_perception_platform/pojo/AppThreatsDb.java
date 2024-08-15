package com.tpp.threat_perception_platform.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName app_threats_db
 */
@Data
public class AppThreatsDb implements Serializable {
    /**
     * 自增id
     */
    private Integer id;

    /**
     * 应用风险对应名
     */
    private String name;

    /**
     * 应用风险描述
     */
    private String desc;

    /**
     * 应用风险等级1 2 3 高中低
     */
    private Integer level;

    /**
     * 验证应用风险代码
     */
    private String pocCode;

    /**
     * 入库时间
     */
    private Date createTime;

    /**
     * 应用种类
     */
    private String type;

    private static final long serialVersionUID = 1L;
}