package com.example.myshop.config;

import com.example.myshop.dto.OrderCanceledMessage;
import com.example.myshop.dto.OrderCompletedMessage;
import com.example.myshop.dto.OrderUpdatedReceiverMessage;
import com.example.myshop.order.domain.OrderEventSender;
import com.example.myshop.order.infrastructure.producer.OrderMessageKafkaProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class OrderEventSenderConfig {

    @Bean
    OrderEventSender orderMessageKafkaProducer(
            KafkaTemplate<Long, OrderCompletedMessage> orderCompletedMessageKafkaTemplate,
            KafkaTemplate<Long, OrderCanceledMessage> orderCanceledMessageKafkaTemplate) {
        return new OrderMessageKafkaProducer(orderCompletedMessageKafkaTemplate, orderCanceledMessageKafkaTemplate);
    }
}
