package com.example.myshop.config.producer;

import com.example.myshop.dto.OrderCanceledMessage;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class OrderCanceledKafkaTemplateConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public KafkaTemplate<Long, OrderCanceledMessage> orderCancelMessageKafkaTemplate() {
        return new KafkaTemplate<>(orderCancelMessageProducerFactory());
    }

    private ProducerFactory<Long, OrderCanceledMessage> orderCancelMessageProducerFactory() {
        return new DefaultKafkaProducerFactory<>(ProducerProps.producerProps(bootstrapServers, "pr-1"));
    }

}
