package com.example.myshop.consumer;

import com.example.myshop.domain.OrderEventHistory;
import com.example.myshop.domain.OrderEventHistoryReader;
import com.example.myshop.domain.OrderEventHistoryStore;
import com.example.myshop.domain.OrderEventType;
import com.example.myshop.dto.OrderCompletedMessage;
import com.example.myshop.order.domain.Order;
import com.example.myshop.order.domain.OrderReader;
import com.example.myshop.service.issuedcoupon.ApplyIssuedCouponService;
import com.example.myshop.service.notification.NotificationService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

import static com.example.myshop.constant.KafkaConst.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderCompletedMessageConsumer {
    private final int defaultCount = 2;
    private final ApplyIssuedCouponService issueCouponService;
    private final OrderEventHistoryReader historyReader;
    private final OrderEventHistoryStore historyStore;
    private final NotificationService notificationService;
    private final OrderReader orderReader;
    @Getter
    private CountDownLatch latch = new CountDownLatch(defaultCount);


    @KafkaListener(id = ORDER_COMPLETED_COUPON_APPLY_GROUP_ID, topics = ORDER_COMPLETED_TOPIC_NAME, containerFactory = "orderCompletedMessageContainerFactory")
    public void couponApplyListener(@Valid OrderCompletedMessage message) {
        issueCouponService.applyIssuedCouponsByOrderId(message.getOrderId());
        latch.countDown();
        log.info("OrderMessageConsumer.handleCoupon = {}", message);
    }

    @KafkaListener(id = ORDER_COMPLETED_GROUP_MESSAGE_GROUP_ID, topics = ORDER_COMPLETED_TOPIC_NAME, containerFactory = "orderCompletedMessageContainerFactory")
    public void messageSendListener(@Valid OrderCompletedMessage message) {
        Order order = orderReader.getOrder(message.getOrderId());
        notificationService.sendSms(order.getDeliveryFragment().getReceiverPhone(), "description");
        latch.countDown();
        log.info("OrderMessageConsumer.handleMessage = {}", message);
    }

    public void resetLatch() {
        latch = new CountDownLatch(3);
    }

    public void resetLatch(int size) {
        latch = new CountDownLatch(size);
    }
}
