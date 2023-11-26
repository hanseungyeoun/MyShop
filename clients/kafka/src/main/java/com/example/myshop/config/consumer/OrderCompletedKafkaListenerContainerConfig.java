package com.example.myshop.config.consumer;

import com.example.myshop.dto.OrderCompletedMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
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
import org.springframework.kafka.listener.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class OrderCompletedKafkaListenerContainerConfig implements KafkaListenerConfigurer {

    private final LocalValidatorFactoryBean validator;
    private final JsonDeserializer<OrderCompletedMessage> deserializer;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public OrderCompletedKafkaListenerContainerConfig(LocalValidatorFactoryBean validator) {
        this.validator = validator;

        this.deserializer = new JsonDeserializer<>(OrderCompletedMessage.class);
        this.deserializer.setRemoveTypeHeaders(false);
        this.deserializer.addTrustedPackages("*");
        this.deserializer.setUseTypeMapperForKey(true);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Long, OrderCompletedMessage>> orderCompletedMessageContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, OrderCompletedMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderCompletedMessageConsumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.RECORD);
        factory.setCommonErrorHandler(new DefaultErrorHandler(new ConsumerRecordRecoverer() {
            @Override
            public void accept(ConsumerRecord<?, ?> consumerRecord, Exception e) {
               // consumerRecord.value()
                System.out.println("OrderCompletedKafkaListenerContainerConfig.accept");
                System.out.println("consumerRecord = " + consumerRecord);
            }
        }, new FixedBackOff(1000L, 2L)));

        return factory;
    }

    public ConsumerFactory<Long, OrderCompletedMessage> orderCompletedMessageConsumerFactory() {
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
