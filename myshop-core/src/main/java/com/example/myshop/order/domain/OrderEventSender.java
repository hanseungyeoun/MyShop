package com.example.myshop.order.domain;

import com.example.myshop.dto.OrderCanceledMessage;
import com.example.myshop.dto.OrderCompletedMessage;
import com.example.myshop.dto.OrderUpdatedReceiverMessage;

public interface OrderEventSender {

    void orderCompleted(OrderCompletedMessage message);

    void paymentCanceled(OrderCanceledMessage message);

    void updatedReceiver(OrderUpdatedReceiverMessage message);
}
