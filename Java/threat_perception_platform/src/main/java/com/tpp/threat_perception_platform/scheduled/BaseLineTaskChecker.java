package com.tpp.threat_perception_platform.scheduled;


import com.alibaba.fastjson.JSON;
import com.tpp.threat_perception_platform.pojo.BaseLineTask;
import com.tpp.threat_perception_platform.service.BaseLineTaskService;
import com.tpp.threat_perception_platform.service.MQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class BaseLineTaskChecker {

    @Autowired
    private BaseLineTaskService baseLineTaskService;

    @Autowired
    private MQService mqService;

    @Scheduled(fixedRate = 10000)  // 每10秒执行一次
    public void checkBaseLineTask() {
        // 先查询出所有状态为0的任务
        List<BaseLineTask> tasks = baseLineTaskService.findUnfinishedTasks();
        for (BaseLineTask task : tasks) {
            Date scheduledTime = task.getScheduledTime();
            Date now = new Date();
            // 如果当前时间大于规定时间，则开始向主机发送执行任务的消息
            if (now.after(scheduledTime)) {
                String exchange = "agent_exchange";
                String hosts = task.getHosts();
                String[] macArray = hosts.split(",");
                for(String mac : macArray){
                    String routingKey = mac.replace(":","");
                    HashMap<String,String> map = new HashMap<>();
                    map.put("type","baseline");
                    map.put("mac",mac);
                    mqService.sendMessage(exchange,routingKey, JSON.toJSONString(map));
                }
                // 发送消息之后，将任务置为已完成
                baseLineTaskService.setTaskStatusCompleted(task);
            }
        }
    }

}
