package com.tpp.threat_perception_platform.scheduled;

import com.alibaba.fastjson.JSON;
import com.tpp.threat_perception_platform.pojo.Host;
import com.tpp.threat_perception_platform.service.HostService;
import com.tpp.threat_perception_platform.service.MQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class HostLogsTagMessager {

    @Autowired
    private HostService hostService;

    @Autowired
    private MQService rabbitMQService;

    @Scheduled(fixedRate = 15000) // 每15秒执行一次
    public void sendLogsTag() {
        // 查询出所有在线的并且配置了同步日志的机器
        List<Host> hosts = hostService.findOnlineHostsAndSyncLogsOn();
        for (Host host : hosts) {
            // 发送消息
            // ...
            String mac = host.getMac();
            String exchange = "agent_exchange";
            String routingKey = mac.replace(":","");
            HashMap<String, String> map = new HashMap<>();
            map.put("type","logs");
            map.put("mac",mac);
            rabbitMQService.sendMessage(exchange,routingKey, JSON.toJSONString(map));
        }

    }

}
