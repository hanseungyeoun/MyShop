package com.example.myshop.order.application;

import com.example.myshop.exception.EntityNotFoundException;
import com.example.myshop.member.domain.Member;
import com.example.myshop.member.domain.MemberReader;
import com.example.myshop.order.domain.Order;
import com.example.myshop.order.domain.fragment.DeliveryFragment;
import com.example.myshop.order.domain.item.OrderItem;
import com.example.myshop.order.domain.item.OrderItemOption;
import com.example.myshop.order.domain.item.OrderItemOptionGroup;
import com.example.myshop.item.domain.item.Item;
import com.example.myshop.item.domain.item.ItemReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;


@RequiredArgsConstructor
@Component
public class OrderMapper {

    private final MemberReader memberReader;
    private final ItemReader itemReader;

    public Order mapFrom(OrderCommand.RegisterOrderCommand command) {
        validateUser(command.getMemberId());

        return Order.builder()
                .memberId(command.getMemberId())
                .payMethod(command.getPayMethod())
                .deliveryFragment(createDeliveryFragment(command))
                .orderItems(command.getOrderItems().stream().map(this::createOrderItem).toList())
                .build();
    }

    private DeliveryFragment createDeliveryFragment(OrderCommand.RegisterOrderCommand delivery) {
        return DeliveryFragment.builder()
                .receiverName(delivery.getReceiverName())
                .receiverPhone(delivery.getReceiverPhone())
                .receiverZipcode(delivery.getReceiverZipcode())
                .receiverAddress1(delivery.getReceiverAddress1())
                .receiverAddress2(delivery.getReceiverAddress2())
                .etcMessage(delivery.getEtcMessage())
                .build();
    }

    private OrderItem createOrderItem(OrderCommand.RegisterOrderItemCommand command) {
        validateItem(command.getItemId());
        return OrderItem.builder()
                .orderCount(command.getOrderCount())
                .itemId(command.getItemId())
                .itemName(command.getItemName())
                .memberId(command.getMemberId())
                .itemPrice(command.getItemPrice())
                .orderItemOptionGroups(command.getOrderItemOptionGroups().stream().map(this::crateOrderItemGroupOption).toList())
                .issueCouponIds(command.getIssueCouponIds())
                .build();
    }

    private OrderItemOptionGroup crateOrderItemGroupOption(OrderCommand.RegisterOrderItemOptionGroupCommand itemOptionGroup) {
        return OrderItemOptionGroup.builder()
                .itemOptionGroupName(itemOptionGroup.getItemOptionGroupName())
                .ordering(itemOptionGroup.getOrdering())
                .orderItemOptions(itemOptionGroup.getOrderItemOptions().stream().map(this::createOrderOption).toList())
                .build();
    }

    private OrderItemOption createOrderOption(OrderCommand.RegisterOrderItemOptionCommand itemOption) {
        return OrderItemOption.builder()
                .ordering(itemOption.getOrdering())
                .itemOptionName(itemOption.getItemOptionName())
                .build();
    }

    private void validateUser(Long memberId) {
        Optional<Member> optionalMember = memberReader.findById(memberId);
        if (optionalMember.isEmpty()) {
            throw new EntityNotFoundException(String.format("member id not found %d", memberId));
        }
    }

    private void validateItem(Long itemId) {
        Optional<Item> optionalItem = itemReader.findById(itemId);
        if (optionalItem.isEmpty()) {
            throw new EntityNotFoundException(String.format("item id not found %d", itemId));
        }
    }
}