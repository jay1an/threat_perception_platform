package com.tpp.threat_perception_platform.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName weak_pwd_dict
 */
@Data
public class WeakPwdDict implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String value;

    private static final long serialVersionUID = 1L;
}