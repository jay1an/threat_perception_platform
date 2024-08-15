package com.tpp.threat_perception_platform.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

/**
 * 账户实体类，表示一个用户账户。
 *
 * @TableName account
 */
@Data
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账户的唯一标识符。
     */
    private Integer id;

    private String mac;

    /**
     * 账户类型。
     */
    private Integer accountType;

    /**
     * 账户的标题。
     */
    private String caption;

    /**
     * 账户的描述。
     */
    private String description;

    /**
     * 指示账户是否被禁用。
     */
    private Integer disabled;

    /**
     * 账户所属的域。
     */
    private String domain;

    /**
     * 账户的全名。
     */
    private String fullName;

    /**
     * 指示是否为本地账户。
     */
    private Integer localAccount;

    /**
     * 指示账户是否被锁定。
     */
    private Integer lockout;

    /**
     * 账户的名称。
     */
    private String name;

    /**
     * 指示密码是否可以更改。
     */
    private Integer passwordChangeable;

    /**
     * 指示密码是否会过期。
     */
    private Integer passwordExpires;

    /**
     * 指示是否需要密码。
     */
    private Integer passwordRequired;

    /**
     * 账户的安全标识符 (SID)。
     */
    private String sid;

    /**
     * SID 的类型。
     */
    private Integer sidType;

    /**
     * 账户的状态。
     */
    private String status;


    // 自定义的 setter 方法来处理布尔类型的 JSON 数据

    @JsonSetter("disabled")
    public void setDisabled(Boolean disabled) {
        this.disabled = disabled != null && disabled ? 1 : 0;
    }

    @JsonSetter("localAccount")
    public void setLocalAccount(Boolean localAccount) {
        this.localAccount = localAccount != null && localAccount ? 1 : 0;
    }

    @JsonSetter("lockout")
    public void setLockout(Boolean lockout) {
        this.lockout = lockout != null && lockout ? 1 : 0;
    }

    @JsonSetter("passwordChangeable")
    public void setPasswordChangeable(Boolean passwordChangeable) {
        this.passwordChangeable = passwordChangeable != null && passwordChangeable ? 1 : 0;
    }

    @JsonSetter("passwordExpires")
    public void setPasswordExpires(Boolean passwordExpires) {
        this.passwordExpires = passwordExpires != null && passwordExpires ? 1 : 0;
    }

    @JsonSetter("passwordRequired")
    public void setPasswordRequired(Boolean passwordRequired) {
        this.passwordRequired = passwordRequired != null && passwordRequired ? 1 : 0;
    }


}
