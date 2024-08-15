package com.tpp.threat_perception_platform.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String currentPassword;
    private String newPassword;

    // getters and setters
}