package com.example.myshop.order.domain.payment;

import com.example.myshop.common.jpa.Money;
import com.example.myshop.order.domain.Order;
import com.example.myshop.order.infrastructure.OrderRequest;

import static com.example.myshop.order.infrastructure.OrderRequest.*;

public interface PaymentProcessor {

    boolean pay(PaymentRequest request);

    boolean cancel(PaymentCancelRequest request);
}
