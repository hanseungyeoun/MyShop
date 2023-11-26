package com.example.myshop.order.interfaces;

import com.example.myshop.order.application.OrderQueryService;
import com.example.myshop.order.application.OrderService;
import com.example.myshop.response.APIDataResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.example.myshop.order.interfaces.OrderDto.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderQueryService orderQueryService;

    private final OrderDtoMapper orderDtoMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public APIDataResponse<RegisterOrderResponse> registerItem(@RequestBody @Valid RegisterOrderRequest request) {
        var orderCommand = orderDtoMapper.of(request);
        var itemId = orderService.registerOrder(orderCommand);
        var response = orderDtoMapper.of(itemId);
        return APIDataResponse.success(response);
    }

    @PostMapping("/{orderId}/payment-order")
    public APIDataResponse<String> paymentOrder(@PathVariable Long orderId, @RequestBody @Valid PaymentRequest paymentRequest) {
        var paymentCommand = orderDtoMapper.of(paymentRequest);
        orderService.paymentOrder(orderId, paymentCommand);
        return APIDataResponse.success("OK");
    }

    @PostMapping("/{orderId}/cancel-order")
    public APIDataResponse<String> paymentOrder(@PathVariable Long orderId, @RequestBody @Valid PaymentCancelRequest request) {
        var paymentCancelCommand = orderDtoMapper.of(request);
        orderService.cancelPaymentOrder(orderId, paymentCancelCommand);
        return APIDataResponse.success("OK");
    }


    @PutMapping("/{orderId}/receiver")
    public APIDataResponse<String> paymentOrder(@PathVariable Long orderId, @RequestBody @Valid OrderDto.UpdateReceiverInfoRequest receiverInfoRequest) {
        var receiverInfoCommand = orderDtoMapper.of(receiverInfoRequest);
        orderService.updateReceiverInfo(orderId, receiverInfoCommand);
        return APIDataResponse.success("OK");
    }
}
