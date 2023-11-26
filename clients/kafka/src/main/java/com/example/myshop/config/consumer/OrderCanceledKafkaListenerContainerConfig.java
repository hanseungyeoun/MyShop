package com.example.myshop.config.consumer;

import com.example.myshop.dto.OrderCanceledMessage;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListenerConfigurer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class OrderCanceledKafkaListenerContainerConfig implements KafkaListenerConfigurer {

    private final LocalValidatorFactoryBean validator;
    private final JsonDeserializer<OrderCanceledMessage> deserializer;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public OrderCanceledKafkaListenerContainerConfig(LocalValidatorFactoryBean validator) {
        this.validator = validator;
        this.deserializer = new JsonDeserializer<>(OrderCanceledMessage.class);
        this.deserializer.setRemoveTypeHeaders(false);
        this.deserializer.addTrustedPackages("*");
        this.deserializer.setUseTypeMapperForKey(true);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Long, OrderCanceledMessage>> orderCanceledMessageContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, OrderCanceledMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderConsumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);

        return factory;
    }

    public ConsumerFactory<Long, OrderCanceledMessage> orderConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                KafkaListenerProps.props(bootstrapServers, this.deserializer),
                new LongDeserializer(),
                this.deserializer
        );
    }

    @Override
    public void configureKafkaListeners(KafkaListenerEndpointRegistrar registrar) {
        registrar.setValidator(validator);
    }
}
