package com.example.myshop.consumer;
import com.example.myshop.domain.OrderEventHistory;
import com.example.myshop.domain.OrderEventHistoryReader;
import com.example.myshop.domain.OrderEventHistoryStore;
import com.example.myshop.domain.OrderEventType;
import com.example.myshop.dto.OrderCanceledMessage;
import com.example.myshop.service.inventory.IncreaseQuantityInventoryFacade;
import com.example.myshop.service.issuedcoupon.ApplyIssuedCouponService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;

import static com.example.myshop.constant.KafkaConst.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderCanceledMessageConsumer {
    private final int defaultCount = 2;

    private final ApplyIssuedCouponService issueCouponService;
    private final IncreaseQuantityInventoryFacade changeQuantityInventoryFacade;
    private final OrderEventHistoryReader historyReader;
    private final OrderEventHistoryStore historyStore;

    @Getter
    private CountDownLatch latch = new CountDownLatch(defaultCount);

    @Transactional
    @KafkaListener(id = ORDER_CANCELED_DECREASE_QUANTITY_GROUP_ID, topics = ORDER_CANCELED_TOPIC_NAME, containerFactory = "orderCanceledMessageContainerFactory")
    public void orderCancelListener(@Valid OrderCanceledMessage message) throws InterruptedException {
        String txId = message.getTxId();
        Long orderId = message.getId();
        storeHistory(orderId, txId);

        changeQuantityInventoryFacade.increaseQuantityByOrderId(orderId);
        latch.countDown();
    }

    @KafkaListener(id = ORDER_CANCELED_COUPON_RESET_GROUP_ID, topics = ORDER_CANCELED_TOPIC_NAME, containerFactory = "orderCanceledMessageContainerFactory")
    public void couponResetListener(@Valid OrderCanceledMessage message) {
        issueCouponService.resetIssuedCouponsByOrderId(message.getId());
        latch.countDown();
        log.info("OrderMessageConsumer.handleCoupon = {}", message);
    }

    private void storeHistory(Long orderId, String txId) {
        OrderEventHistory history = new OrderEventHistory(orderId, txId, OrderEventType.ORDER_CANCELED);
        historyStore.store(history);
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public void resetLatch(int count) {
        latch = new CountDownLatch(count);
    }
}
