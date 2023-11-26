package com.example.myshop.order.application;

public interface OrderService {

    Long registerOrder(OrderCommand.RegisterOrderCommand command);

    void paymentOrder(Long orderId, OrderCommand.PaymentCommand command);

    void updateReceiverInfo(Long orderId, OrderCommand.UpdateReceiverInfoCommand command);

    void cancelPaymentOrder(Long orderId, OrderCommand.PaymentCancelCommand command);
}
