package com.tpp.threat_perception_platform.service.impl;

import com.tpp.threat_perception_platform.dto.DashboardDataDTO;
import com.tpp.threat_perception_platform.mapper.*;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.DashboardDataService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Service
public class DashboardDataServiceImpl implements DashboardDataService {

    @Autowired
    private HostMapper hostMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private BaseLineTaskMapper baseLineTaskMapper;

    @Autowired
    private HostAppThreatMapper hostAppThreatMapper;

    @Autowired
    private HostBaselineMapper hostBaselineMapper;

    @Autowired
    private HostLogsMapper hostLogsMapper;

    @Autowired
    private HostVulnerabilityMapper hostVulnerabilityMapper;

    @Autowired
    private HostWeakPwdMapper hostWeakPwdMapper;

    @Autowired
    private HotfixMapper hotfixMapper;

    @Autowired
    private ProcessMapper processMapper;

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private AppMapper appMapper;

    private HashMap<String, Integer> initializeAbnormalMap() {
        HashMap<String, Integer> newMap = new HashMap<>();
        newMap.put("baselineAbnormal", 0);
        newMap.put("vulAbnormal", 0);
        newMap.put("hotfixAbnormal", 0);
        newMap.put("weakPwdAbnormal", 0);
        newMap.put("appAbnormal", 0);
        return newMap;
    }

    @Override
    public ResponseResult<?> getDashboardData() {
        DashboardDataDTO dashboardData = null;
        try {
            dashboardData = new DashboardDataDTO();
            // 日志数量
            int logCount = hostLogsMapper.selectCount();
            dashboardData.setLogCount(logCount);
            // 基线检查结果数量
            int baselineResCount = hostBaselineMapper.selectCount();
            dashboardData.setBaselineResCount(baselineResCount);
            // 未完成基线检查任务数量
            int baselineUnfinishedTaskCount = baseLineTaskMapper.selectUnfinishedCount();
            dashboardData.setBaselineUnfinishedTaskCount(baselineUnfinishedTaskCount);
            // 异常主机
            HashMap<String, HashMap<String, Integer>> abnormalHosts = new HashMap<>();
            List<String> baselineAbnormalHosts = hostBaselineMapper.selectAbnormalHosts();  // 基线异常主机
            List<String> vulAbnormalHosts = hostVulnerabilityMapper.selectAbnormalHosts(); // 漏洞异常主机
            List<String> hotfixAbnormalHosts = hostVulnerabilityMapper.selectAbnormalHosts(); // 热补丁异常主机
            List<String> weakPwdAbnormalHosts = hostWeakPwdMapper.selectAbnormalHosts(); // 弱口令异常主机
            List<String> appAbnormalHosts = hostAppThreatMapper.selectAbnormalHosts(); // 应用异常主机
            // 处理基线异常主机
            // 处理基线异常主机
            for (String mac : baselineAbnormalHosts) {
                if (!abnormalHosts.containsKey(mac)) {
                    abnormalHosts.put(mac, initializeAbnormalMap());
                }
                HashMap<String, Integer> hostMap = abnormalHosts.get(mac);
                hostMap.put("baselineAbnormal", hostMap.get("baselineAbnormal") + 1);
            }

            // 处理漏洞异常主机
            for (String mac : vulAbnormalHosts) {
                if (!abnormalHosts.containsKey(mac)) {
                    abnormalHosts.put(mac, initializeAbnormalMap());
                }
                HashMap<String, Integer> hostMap = abnormalHosts.get(mac);
                hostMap.put("vulAbnormal", hostMap.get("vulAbnormal") + 1);
            }

            // 处理热补丁异常主机
            for (String mac : hotfixAbnormalHosts) {
                if (!abnormalHosts.containsKey(mac)) {
                    abnormalHosts.put(mac, initializeAbnormalMap());
                }
                HashMap<String, Integer> hostMap = abnormalHosts.get(mac);
                hostMap.put("hotfixAbnormal", hostMap.get("hotfixAbnormal") + 1);
            }

            // 处理弱口令异常主机
            for (String mac : weakPwdAbnormalHosts) {
                if (!abnormalHosts.containsKey(mac)) {
                    abnormalHosts.put(mac, initializeAbnormalMap());
                }
                HashMap<String, Integer> hostMap = abnormalHosts.get(mac);
                hostMap.put("weakPwdAbnormal", hostMap.get("weakPwdAbnormal") + 1);
            }

            // 处理应用异常主机
            for (String mac : appAbnormalHosts) {
                if (!abnormalHosts.containsKey(mac)) {
                    abnormalHosts.put(mac, initializeAbnormalMap());
                }
                HashMap<String, Integer> hostMap = abnormalHosts.get(mac);
                hostMap.put("appAbnormal", hostMap.get("appAbnormal") + 1);
            }
            dashboardData.setAbnormalHosts(abnormalHosts);
            // 威胁数量 = 应用威胁数量 + 漏洞威胁数量 + 热补丁威胁数量 + 弱口令威胁数量
            int threatsCount;
            // 应用威胁数量
            int threatAppCount = hostAppThreatMapper.selectCount();
            dashboardData.setThreatAppCount(threatAppCount);
            // 漏洞威胁数量
            int threatVulnerabilityCount = hostVulnerabilityMapper.selectCount();
            dashboardData.setThreatVulnerabilityCount(threatVulnerabilityCount);
            // 热补丁威胁数量
            int threatHotfixCount = hotfixMapper.selectAbnormalCount();
            dashboardData.setThreatHotfixCount(threatHotfixCount);
            // 弱口令威胁数量
            int threatWeakPwdCount = hostWeakPwdMapper.selectCount();
            dashboardData.setThreatWeakPwdCount(threatWeakPwdCount);
            // 应用威胁数量 + 漏洞威胁数量 + 热补丁威胁数量 + 弱口令威胁数量
            threatsCount = threatAppCount + threatVulnerabilityCount + threatHotfixCount + threatWeakPwdCount;
            dashboardData.setThreatsCount(threatsCount);
            // 异常基线数量
            int abnormalBaselineCount = hostBaselineMapper.selectAbnormalCount();
            dashboardData.setAbnormalBaselineCount(abnormalBaselineCount);
            // 在线主机数量
            int onlineHostCount = hostMapper.selectCountbyStatus(1);
            dashboardData.setOnlineHostCount(onlineHostCount);
            // 离线主机数量
            int offlineHostCount = hostMapper.selectCountbyStatus(0);
            dashboardData.setOfflineHostCount(offlineHostCount);
            // 主机类型统计(windows,linux,macOS)
            List<String> osName = hostMapper.selectOsName();
            HashMap<String, Integer> windowsCountMap = new HashMap<>();
            int linuxCount = 0;
            int macOSCount = 0;
            for (String os : osName) {
                if (os.contains("Windows")) {
                    // 提取Windows大版本信息
                    String windowsVersion = "";
                    if (os.contains("Windows 10")) {
                        windowsVersion = "Windows 10";
                    } else if (os.contains("Windows 11")) {
                        windowsVersion = "Windows 11";
                    } else if (os.contains("Windows Server")) {
                        windowsVersion = "Windows Server";
                    }
                    // 更新Windows版本计数
                    windowsCountMap.put(windowsVersion, windowsCountMap.getOrDefault(windowsVersion, 0) + 1);
                } else if (os.contains("Linux")) {
                    linuxCount++;
                } else if (os.contains("macOS") || os.contains("MacOS")) {
                    macOSCount++;
                }
            }
            dashboardData.setWindowsCountMap(windowsCountMap);
            dashboardData.setLinuxCount(linuxCount);
            dashboardData.setMacOSCount(macOSCount);
            // 账号数量
            int accountCount = accountMapper.selectCount();
            dashboardData.setAccountCount(accountCount);
            // 服务数量
            int serviceCount = serviceMapper.selectCount();
            dashboardData.setServiceCount(serviceCount);
            // 进程数量
            int processCount = processMapper.selectCount();
            dashboardData.setProcessCount(processCount);
            // 应用数量
            int appCount = appMapper.selectCount();
            dashboardData.setAppCount(appCount);
            return new ResponseResult<>(0, dashboardData);
        } catch (Exception e) {
            return new ResponseResult<>(-1, e.getMessage());
        }
    }
}
