package com.tpp.threat_perception_platform.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.tpp.threat_perception_platform.pojo.Host;
import com.tpp.threat_perception_platform.service.HostService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Map;


@Component
public class RabbitMQStatusInfoConsumer {

    @Autowired
    private HostService hostService;

    @RabbitListener(queues = {"status_queue"})
    public void consume(String message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            // 直接解析 JSON 字符串并提取 "mac" 字段
            JSONObject jsonObject = JSON.parseObject(message);
            String mac = jsonObject.getString("mac");
            int status = (int)jsonObject.getInteger("status");

            // 更新主机状态为在线
            if(status==1){
                hostService.updateStatusOnline(mac);
            }else if(status==0){
                hostService.updateStatusOffline(mac);
            }
            channel.basicAck(deliveryTag, false);

        } catch (Exception e) {
            channel.basicReject(deliveryTag, true);
            e.printStackTrace();
        }
    }
}
