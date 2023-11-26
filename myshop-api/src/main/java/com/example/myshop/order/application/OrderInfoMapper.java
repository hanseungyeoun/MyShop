package com.example.myshop.order.application;

import com.example.myshop.order.domain.OrderInfo;
import com.example.myshop.common.jpa.Money;
import com.example.myshop.order.domain.Order;
import com.example.myshop.order.domain.item.OrderItem;
import com.example.myshop.order.domain.item.OrderItemOption;
import com.example.myshop.order.domain.item.OrderItemOptionGroup;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderInfoMapper {

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "deliveryFragment", target = "deliveryInfo")
    @Mapping(source = "totalAmount", target = "totalAmount", qualifiedByName = "moneyToInteger")
    @Mapping(expression = "java(order.getStatus().name())", target = "status")
    @Mapping(expression = "java(order.getStatus().getDescription())", target = "statusDescription")
    OrderInfo.Main of(Order order);

    @Mapping(source = "id", target = "orderItemId")
    @Mapping(source = "itemPrice", target = "itemPrice", qualifiedByName = "moneyToInteger")
    @Mapping(source = "totalAmount", target = "totalAmount", qualifiedByName = "moneyToInteger")
    @Mapping(expression = "java(orderItem.getDeliveryStatus().name())", target = "deliveryStatus")
    @Mapping(expression = "java(orderItem.getDeliveryStatus().getDescription())", target = "deliveryStatusDescription")
    OrderInfo.OrderItemInfo of(OrderItem orderItem);

    @Mapping(source = "id", target = "optionGroupId")
    OrderInfo.OrderItemOptionGroupInfo of(OrderItemOptionGroup orderItemOptionGroup);

    @Mapping(source = "id", target = "optionId")
    OrderInfo.OrderItemOptionInfo of(OrderItemOption orderItemOption);

    @Named("moneyToInteger")
    static Integer moneyToInteger(Money itemPrice) {
        return itemPrice == null ? null : itemPrice.getMoney().intValue();
    }
}
