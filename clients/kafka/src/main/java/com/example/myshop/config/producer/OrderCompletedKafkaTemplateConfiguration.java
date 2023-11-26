package com.example.myshop.config.producer;

import com.example.myshop.dto.OrderCompletedMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class OrderCompletedKafkaTemplateConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public KafkaTemplate<Long, OrderCompletedMessage> orderCompleteMessageKafkaTemplate() {
        return new KafkaTemplate<>(orderCompleteMessageProducerFactory());
    }

    private ProducerFactory<Long, OrderCompletedMessage> orderCompleteMessageProducerFactory() {
        return new DefaultKafkaProducerFactory<>(ProducerProps.producerProps(bootstrapServers, "pr-0"));
    }
}
