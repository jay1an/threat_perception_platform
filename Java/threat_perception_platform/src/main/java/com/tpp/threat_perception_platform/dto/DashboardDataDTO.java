package com.tpp.threat_perception_platform.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class DashboardDataDTO {
    private Integer logCount;   // 日志数量
    private Integer baselineResCount;  // 基线检测结果数量
    private Integer baselineUnfinishedTaskCount; // 未完成基线检测任务数量
    private HashMap<String, HashMap<String,Integer>> abnormalHosts;  // 异常主机
    private Integer threatsCount;   // 威胁数量
    private Integer abnormalBaselineCount; // 异常基线数量
    private Integer onlineHostCount; // 在线主机数量
    private Integer offlineHostCount; // 离线主机数量
    private HashMap<String, Integer> windowsCountMap;  // Windows主机数量
    private Integer linuxCount; // Linux主机数量
    private Integer macOSCount; // MacOS主机数量
    private Integer threatHotfixCount;  // 威胁补丁数量
    private Integer threatVulnerabilityCount; // 威胁漏洞数量
    private Integer threatWeakPwdCount; // 威胁弱口令数量
    private Integer threatAppCount; // 威胁应用数量
    private Integer accountCount; // 账号数量
    private Integer serviceCount; // 服务数量
    private Integer processCount; // 进程数量
    private Integer appCount;    // 应用数量
}
