package com.tpp.threat_perception_platform.param;


import com.tpp.threat_perception_platform.pojo.AppThreatsDb;
import com.tpp.threat_perception_platform.pojo.Vulnerability;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThreatsParam {

    private String hostname;

    private String mac;

    private Integer hotfix = 0;  // 是否需要补丁检测，1为需要，0为不需要

    private Integer vulnerability = 0;    // 漏洞检测

    private Integer weakPwd = 0;        // 弱密码检测

    private Integer app = 0;     // 风险应用检测

    private Integer system = 0;  // 系统风险检测

    // 漏洞检测需要用到参数
    private List<Vulnerability> vulnerabilities; // 漏洞列表（一同发送至agent）

    // 弱口令检测需要用到的参数
    private List<String> weakPwds;

    private List<String> usernames;

    private HashMap<String, List<String>> weakPwdServiceMap;

    // 应用风险需要用到的参数
    private HashMap<String,List<String>> appThreatsServiceMap;  // 要检测的服务：端口号

    private HashMap<String,List<AppThreatsDb>> appThreatsDbMap;       // 要检测的服务：对应的数据（比如说tomcat可能有多个漏洞）

    private String type = "threats";
}
