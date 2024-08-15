package com.tpp.threat_perception_platform.pojo;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 
 * @TableName role
 */
@Data
public class Role implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 角色名
     */
    @NotEmpty
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDesc;

    private static final long serialVersionUID = 1L;
}