package com.tpp.threat_perception_platform.param;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetsParam {
    private String hostname;

    private String mac;

    private Integer account = 0;  // 是否需要账号探测，1为需要，0为不需要

    private Integer service = 0;    // 服务探测

    private Integer app = 0;        // 应用程序探测

    private Integer process = 0;     // 进程探测

    private String type = "assets";
}
