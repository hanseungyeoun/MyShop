package com.example.myshop.order.application;

import com.example.myshop.dto.OrderCompletedMessage;
import com.example.myshop.exception.InvalidParamException;
import com.example.myshop.order.domain.OrderEventSender;
import com.example.myshop.order.domain.*;
import com.example.myshop.order.domain.discount.DiscountCalculator;
import com.example.myshop.dto.OrderCanceledMessage;
import com.example.myshop.order.domain.payment.PaymentProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.example.myshop.order.application.OrderCommand.*;
import static com.example.myshop.order.infrastructure.OrderRequest.*;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderStore orderStore;
    private final OrderReader orderReader;
    private final OrderMapper orderMapper;
    private final PaymentProcessor paymentProcessor;
    private final OrderValidator orderValidatorImpl;
    private final DiscountCalculator discountCalculator;
    private final OrderEventSender orderEventSender;
    private final InventoryFacade inventoryFacade;

    @Override
    public Long registerOrder(RegisterOrderCommand command) {
        Order order = orderMapper.mapFrom(command);
        Order savedOrder = orderStore.store(order);
        order.calculateTotalAmount(discountCalculator);
        return savedOrder.getId();
    }

    @Override
    public void paymentOrder(Long orderId, PaymentCommand command) {
        var order = orderReader.getOrder(orderId);
        validatePaymentValidation(order, command, discountCalculator);
        order.validation(orderValidatorImpl);
        inventoryFacade.decreaseQuantityByOrderId(orderId);
        Boolean paymentResult = paymentProcessor.pay(new PaymentRequest(command.getAmount(), command.getPayMethod(), command.getOrderDescription() ));

        if (paymentResult) {
            order.orderComplete();
            orderEventSender.orderCompleted(new OrderCompletedMessage(orderId, UUID.randomUUID().toString()));
        } else {
            inventoryFacade.increaseQuantityByOrderId(orderId);
        }
        log.info("OrderServiceImpl.paymentOrder");
    }

    @Override
    public void cancelPaymentOrder(Long orderId, PaymentCancelCommand command) {
        var order = orderReader.getOrder(orderId);
        validatePaymentCancelValidation(order, command, discountCalculator);
        Boolean cancelResult = paymentProcessor.cancel(new PaymentCancelRequest(command.getAmount(), command.getPayMethod(), command.getOrderDescription()));

        if (cancelResult) {
            orderEventSender.paymentCanceled(new OrderCanceledMessage(order.getId(), UUID.randomUUID().toString()));
            order.orderCancel();
        }
    }

    @Override
    public void updateReceiverInfo(Long orderId, UpdateReceiverInfoCommand command) {
        var order = orderReader.getOrder(orderId);
        order.updateDeliveryFragment(
                command.getReceiverName(),
                command.getReceiverPhone(),
                command.getReceiverZipcode(),
                command.getReceiverAddress1(),
                command.getReceiverAddress2(),
                command.getEtcMessage()
        );
    }

    //TODO  도메인 로직으로 통합 교려
    private void validatePaymentValidation(Order order, PaymentCommand paymentRequest, DiscountCalculator discountCalculator) {
        if (!order.getPayMethod().equals(paymentRequest.getPayMethod().name())) {
            throw new InvalidParamException("주문 과정에서의 결제수단이 다릅니다.");
        }

        if (!order.calculateTotalAmount(discountCalculator).equals(paymentRequest.getAmount()))
            throw new InvalidParamException(String.format("주문가격이 불일치합니다 %s, %s",
                    order.calculateTotalAmount(discountCalculator),
                    paymentRequest.getAmount()));


        if (order.isAlreadyPaymentComplete()) throw new InvalidParamException("이미 결제완료된 주문입니다");
    }

    private void validatePaymentCancelValidation(Order order, PaymentCancelCommand paymentRequest, DiscountCalculator discountCalculator) {
        if (!order.getPayMethod().equals(paymentRequest.getPayMethod().name())) {
            throw new InvalidParamException("주문 과정에서의 결제수단이 다릅니다.");
        }

        if (!order.calculateTotalAmount(discountCalculator).isGreaterThanOrEqual(paymentRequest.getAmount()))
            throw new InvalidParamException(String.format("취소 금액 오류 %s, %s",
                    order.calculateTotalAmount(discountCalculator),
                    paymentRequest.getAmount()));
    }
}
