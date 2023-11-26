package com.example.myshop.order.interfaces;

import com.example.myshop.common.jpa.Money;
import com.example.myshop.order.application.OrderCommand;
import com.example.myshop.order.domain.OrderInfo;
import org.mapstruct.*;

import static com.example.myshop.order.interfaces.OrderDto.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderDtoMapper {

    @Mapping(source = "itemPrice", target = "itemPrice", qualifiedByName = "integerToMoney")
    OrderCommand.RegisterOrderItemCommand of(RegisterOrderItemRequest request);

    OrderCommand.RegisterOrderCommand of(RegisterOrderRequest request);

    RegisterOrderResponse of(Long id);

    @Mapping(source = "amount", target = "amount", qualifiedByName = "integerToMoney")
    OrderCommand.PaymentCommand of(PaymentRequest request);

    @Mapping(source = "amount", target = "amount", qualifiedByName = "integerToMoney")
    OrderCommand.PaymentCancelCommand of(PaymentCancelRequest request);

    OrderCommand.UpdateReceiverInfoCommand of(UpdateReceiverInfoRequest request);

    @Mapping(source = "orderedAt", target = "orderedAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    OrderResponseDto.Main of(OrderInfo.Main mainResult);

    @Named("integerToMoney")
    static Money integerToMoney(Integer itemPrice) {
        return itemPrice == null ? null : Money.valueOf(itemPrice);
    }

}
