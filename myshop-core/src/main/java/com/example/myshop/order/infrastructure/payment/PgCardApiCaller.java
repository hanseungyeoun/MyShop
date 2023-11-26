package com.example.myshop.order.infrastructure.payment;

import com.example.myshop.order.domain.payment.PayMethod;
import com.example.myshop.order.infrastructure.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PgCardApiCaller implements PaymentApiCaller {

    @Override
    public boolean support(PayMethod payMethod) {
        return PayMethod.CARD == payMethod;
    }

    @Override
    public boolean pay(OrderRequest.PaymentRequest request) {
        // TODO - 구현
        return true;
    }

    @Override
    public boolean cancel(OrderRequest.PaymentCancelRequest request) {
        // TODO - 구현
        return true;
    }
}
