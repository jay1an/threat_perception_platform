package com.tpp.threat_perception_platform.service.impl;

import com.tpp.threat_perception_platform.service.MQService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQServiceImpl implements MQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void createAgentQueue(String exchange, String queueName, String routingKey) {
        // 创建了一个新的队列
        rabbitTemplate.execute(channel -> {
            // 声明队列、绑定队列
            channel.queueDeclare(queueName,true,false,false,null);
            channel.queueBind(queueName,exchange,routingKey);
            return null;
        });
    }

    @Override
    public void sendMessage(String exchange, String routingKey, String data) {
        rabbitTemplate.convertAndSend(exchange,routingKey,data);
    }


}
