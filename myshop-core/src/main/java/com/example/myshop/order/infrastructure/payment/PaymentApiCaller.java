package com.example.myshop.order.infrastructure.payment;

import com.example.myshop.order.domain.payment.PayMethod;
import com.example.myshop.order.infrastructure.OrderRequest;

import static com.example.myshop.order.infrastructure.OrderRequest.*;

public interface PaymentApiCaller {

    boolean support(PayMethod payMethod);
    boolean pay(PaymentRequest request);
    boolean cancel(PaymentCancelRequest request);
}
