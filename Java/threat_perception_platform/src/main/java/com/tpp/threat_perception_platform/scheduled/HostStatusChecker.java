package com.tpp.threat_perception_platform.scheduled;

import com.tpp.threat_perception_platform.pojo.Host;
import com.tpp.threat_perception_platform.service.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class HostStatusChecker {

    @Autowired
    private HostService hostService;

    @Scheduled(fixedRate = 30000) // 每30秒执行一次
    public void checkHostStatus() {
        List<Host> hosts = hostService.findOnlineHosts();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        for (Host host : hosts) {
            if (host.getUpdateTime() != null) {
                long diff = now.getTime() - host.getUpdateTime().getTime();
                if (diff > 30000) { // 如果超过30秒没有收到心跳包
                    hostService.updateStatusOffline(host.getMac());
                }
            } else {
                hostService.updateStatusOffline(host.getMac());
            }
        }
    }
}
