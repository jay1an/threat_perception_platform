package com.tpp.threat_perception_platform.consumer;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.tpp.threat_perception_platform.pojo.*;
import com.tpp.threat_perception_platform.pojo.Process;
import com.tpp.threat_perception_platform.service.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;

import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

@Component
public class RabbitMQSysInfoConsumer {

    @Autowired
    private HostService hostService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ProcessService processService;

    @Autowired
    private AppService appService;

    @Autowired
    private HotfixService hotfixService;

    @Autowired
    private HostVulnerabilityService hostVulnerabilityService;

    @Autowired
    private HostWeakPwdService hostWeakPwdService;

    @Autowired
    private HostAppThreatService hostAppThreatService;

    @Autowired
    private HostLogsService hostLogsService;

    @Autowired
    private HostBaselineService hostBaselineService;

    /**
     * 监听RabbitMQ中的sysinfo_queue队列，用于接收主机信息。
     * 接收到消息后，尝试将主机信息保存到数据库中。如果保存成功，则确认（ACK）消息处理；
     * 如果保存失败，由于唯一性约束（如重复的MAC地址），则也确认消息处理；
     * 如果出现其他异常，则拒绝（Reject）消息处理，且要求重新投递。
     *
     * @param message 接收到的主机信息字符串，由JSON序列化而来。
     * @param headers 消息头，包含交付标签（Delivery Tag）等信息。
     * @param channel RabbitMQ的通道，用于确认或拒绝消息处理。
     * @throws IOException 如果与RabbitMQ的通信出现异常。
     */
    // 监听sysinfo_queue队列
    @RabbitListener(queues = {"sysinfo_queue"})
    public void consumeBasicSysInfo(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        // 将消息字符串反序列化为Host对象
        Host host = JSON.parseObject(message, Host.class);
        // 从消息头中获取交付标签
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        try {
            // 尝试保存主机信息到数据库
            // 保存消息到数据库
            if (hostService.save(host) > 0) {
                // 如果保存成功，确认消息处理
                // 如果保存成功，则ACK消息
                channel.basicAck(deliveryTag, false);
            }
        } catch (DuplicateKeyException e) {
            // 如果出现唯一性约束异常，视为消息处理成功，确认消息
            // 如果发生数据库唯一约束异常（例如重复的MAC地址），则直接ACK消息
            channel.basicAck(deliveryTag, false);
            System.out.println("MAC地址已存在，此条消息无效");
        } catch (Exception e) {
            // 对于其他异常，拒绝消息处理并要求重新投递
            // 其他异常情况，拒绝消息并重新投递
            channel.basicReject(deliveryTag, true);
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = {"account_queue"})
    public void consumeAccountsAssetsDetectionData(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        List<Account> accounts = JSON.parseArray(message, Account.class);
        accountService.processAccounts(accounts);
        channel.basicAck(deliveryTag, false);
    }

    @RabbitListener(queues = {"service_queue"})
    public void consumeServiceAssetsDetectionData(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        List<Service> services = JSON.parseArray(message, Service.class);
        serviceService.processServices(services);
        channel.basicAck(deliveryTag, false);
    }

    @RabbitListener(queues = {"process_queue"})
    public void consumeProcessAssetsDetectionData(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        List<Process> processes = JSON.parseArray(message, Process.class);
        processService.processProcesses(processes);
        channel.basicAck(deliveryTag, false);
    }

    @RabbitListener(queues = {"app_queue"})
    public void consumeAppAssetsDetectionData(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        List<App> apps = JSON.parseArray(message, App.class);
        appService.processApps(apps);
        channel.basicAck(deliveryTag, false);
    }

    @RabbitListener(queues = {"hotfix_queue"})
    public void consumeHotfixThreatDiscoveryData(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        List<Hotfix> hotfixes = JSON.parseArray(message, Hotfix.class);
        hotfixService.processHotfixes(hotfixes);
        channel.basicAck(deliveryTag, false);
    }


    @RabbitListener(queues = {"vulnerability_queue"})
    public void consumeVulnerabilityThreatDiscoveryData(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        if(message.length()==17){
            hostVulnerabilityService.deleteByMac(message);
        }else{
            List<HostVulnerability> hostVulnerabilities = JSON.parseArray(message, HostVulnerability.class);
            hostVulnerabilityService.processHostVulnerabilities(hostVulnerabilities);
        }
        channel.basicAck(deliveryTag, false);
    }

    @RabbitListener(queues = {"weak_pwd_queue"})
    public void consumeWeakPwdThreatDiscoveryData(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        if(message.length()==17){   // 没检测到任何弱密码，就会直接返回一个mac地址
            hostWeakPwdService.deleteByMac(message);
        }else{
            List<HostWeakPwd> hostWeakPwds = JSON.parseArray(message, HostWeakPwd.class);
            hostWeakPwdService.processHostWeakPwds(hostWeakPwds);
        }
        channel.basicAck(deliveryTag, false);
    }

    @RabbitListener(queues = {"app_threats_queue"})
    public void consumeAppThreatDiscoveryData(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        if(message.length()==17){
            hostAppThreatService.deleteByMac(message);
        }else{
            List<HostAppThreat> hostAppThreats = JSON.parseArray(message, HostAppThreat.class);
            hostAppThreatService.processHostAppThreats(hostAppThreats);
        }
        channel.basicAck(deliveryTag, false);
    }

    @RabbitListener(queues = {"logs_queue"})
    public void consumeHostLogsData(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        List<HostLogs> hostLogsList = JSON.parseArray(message, HostLogs.class);
        hostLogsService.processHostLogs(hostLogsList);
        channel.basicAck(deliveryTag, false);
    }


    @RabbitListener(queues = {"baseline_queue"})
    public void consumeHostBaselineData(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        List<HostBaseline> hostBaselines = JSON.parseArray(message, HostBaseline.class);
        hostBaselineService.processHostBaselines(hostBaselines);
        channel.basicAck(deliveryTag, false);
    }

}
