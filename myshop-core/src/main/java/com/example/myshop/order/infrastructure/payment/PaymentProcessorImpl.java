package com.example.myshop.order.infrastructure.payment;

import com.example.myshop.exception.InvalidParamException;
import com.example.myshop.order.domain.payment.PayMethod;
import com.example.myshop.order.domain.payment.PaymentProcessor;
import com.example.myshop.order.infrastructure.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PaymentProcessorImpl implements PaymentProcessor {

    private final List<PaymentApiCaller> paymentApiCallerList;

    @Override
    public boolean pay(OrderRequest.PaymentRequest request) {
        PaymentApiCaller payApiCaller = routingApiCaller(request.getPayMethod());
        return payApiCaller.pay(request);
    }

    @Override
    public boolean cancel(OrderRequest.PaymentCancelRequest request) {
        PaymentApiCaller payApiCaller = routingApiCaller(request.getPayMethod());
        return payApiCaller.cancel(request);
    }

    private PaymentApiCaller routingApiCaller(PayMethod method) {
        return paymentApiCallerList.stream()
                .filter(paymentApiCaller -> paymentApiCaller.support(method))
                .findFirst()
                .orElseThrow(InvalidParamException::new);
    }
}