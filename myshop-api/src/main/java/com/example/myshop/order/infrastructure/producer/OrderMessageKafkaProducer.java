package com.example.myshop.order.infrastructure.producer;

import com.example.myshop.dto.OrderUpdatedReceiverMessage;
import com.example.myshop.order.domain.OrderEventSender;
import com.example.myshop.dto.OrderCanceledMessage;
import com.example.myshop.dto.OrderCompletedMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

import static com.example.myshop.constant.KafkaConst.*;

@Slf4j
public class OrderMessageKafkaProducer implements OrderEventSender {

    private final KafkaTemplate<Long, OrderCompletedMessage> orderCompleteMessageKafkaTemplate;
    private final KafkaTemplate<Long, OrderCanceledMessage> orderCancelMessageKafkaTemplate;

    public OrderMessageKafkaProducer(
            KafkaTemplate<Long, OrderCompletedMessage> orderCompleteKafkaTemplate,
            KafkaTemplate<Long, OrderCanceledMessage> orderCancelMessageKafkaTemplate
    ) {
        this.orderCompleteMessageKafkaTemplate = orderCompleteKafkaTemplate;
        this.orderCancelMessageKafkaTemplate = orderCancelMessageKafkaTemplate;
    }

    @Override
    public void orderCompleted(OrderCompletedMessage message) {
        orderCompleteMessageKafkaTemplate.send(ORDER_COMPLETED_TOPIC_NAME, message.getOrderId(), message);
        log.debug("OrderMessageKafkaProducer.orderCompleted");
    }

    @Override
    public void paymentCanceled(OrderCanceledMessage message) {
        orderCancelMessageKafkaTemplate.send(ORDER_CANCELED_TOPIC_NAME, message.getId(), message);
        log.debug("OrderMessageKafkaProducer.paymentCanceled");
    }

    @Override
    public void updatedReceiver(OrderUpdatedReceiverMessage message) {
        log.debug("OrderMessageKafkaProducer.updatedReceiver");
    }
}
