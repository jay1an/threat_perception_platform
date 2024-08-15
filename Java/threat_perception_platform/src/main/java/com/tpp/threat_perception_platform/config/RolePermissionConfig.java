package com.tpp.threat_perception_platform.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RolePermissionConfig {
    @Bean
    public Map<Integer, String> rolePermissionsMap() {
        Map<Integer, String> rolePermissionsMap = new HashMap<>();
        rolePermissionsMap.put(1, "ADMIN");
        rolePermissionsMap.put(2, "LOGS");
        rolePermissionsMap.put(3, "BASELINE");
        rolePermissionsMap.put(4, "THREAT");
        return rolePermissionsMap;
    }

}
