package com.example.myshop.order.application;

import com.example.myshop.dto.OrderCompletedMessage;
import com.example.myshop.dto.OrderUpdatedReceiverMessage;
import com.example.myshop.order.domain.Order;
import com.example.myshop.order.domain.fragment.DeliveryFragment;
import com.example.myshop.order.domain.item.OrderItem;
import com.example.myshop.order.domain.item.OrderItemOption;
import com.example.myshop.order.domain.item.OrderItemOptionGroup;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-04T14:54:19+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class OrderMessageMapperImpl implements OrderMessageMapper {

    private final DateTimeFormatter dateTimeFormatter_yyyy_MM_dd_HH_mm_ss_11333195168 = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" );

    @Override
    public OrderCompletedMessage.Main of(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderCompletedMessage.Main main = new OrderCompletedMessage.Main();

        main.setDeliveryInfo( deliveryFragmentToDeliveryInfo( order.getDeliveryFragment() ) );
        main.setTotalAmount( OrderMessageMapper.moneyToInteger( order.getTotalAmount() ) );
        if ( order.getOrderedAt() != null ) {
            main.setOrderedAt( dateTimeFormatter_yyyy_MM_dd_HH_mm_ss_11333195168.format( order.getOrderedAt() ) );
        }
        main.setId( order.getId() );
        main.setMemberId( order.getMemberId() );
        main.setPayMethod( order.getPayMethod() );
        main.setOrderItems( orderItemListToOrderItemInfoList( order.getOrderItems() ) );

        main.setTxId( UUID.randomUUID().toString() );
        main.setStatus( order.getStatus().name() );

        return main;
    }

    @Override
    public OrderCompletedMessage.OrderItemInfo of(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }

        OrderCompletedMessage.OrderItemInfo orderItemInfo = new OrderCompletedMessage.OrderItemInfo();

        orderItemInfo.setItemPrice( OrderMessageMapper.moneyToInteger( orderItem.getItemPrice() ) );
        orderItemInfo.setTotalAmount( OrderMessageMapper.moneyToInteger( orderItem.getTotalAmount() ) );
        orderItemInfo.setId( orderItem.getId() );
        orderItemInfo.setOrderCount( orderItem.getOrderCount() );
        orderItemInfo.setItemId( orderItem.getItemId() );
        orderItemInfo.setItemName( orderItem.getItemName() );
        orderItemInfo.setMemberId( orderItem.getMemberId() );
        orderItemInfo.setOrderItemOptionGroups( orderItemOptionGroupListToOrderItemOptionGroupInfoList( orderItem.getOrderItemOptionGroups() ) );

        orderItemInfo.setDeliveryStatus( orderItem.getDeliveryStatus().name() );
        orderItemInfo.setDeliveryStatusDescription( orderItem.getDeliveryStatus().getDescription() );

        return orderItemInfo;
    }

    @Override
    public OrderCompletedMessage.OrderItemOptionGroupInfo of(OrderItemOptionGroup orderItemOptionGroup) {
        if ( orderItemOptionGroup == null ) {
            return null;
        }

        OrderCompletedMessage.OrderItemOptionGroupInfo orderItemOptionGroupInfo = new OrderCompletedMessage.OrderItemOptionGroupInfo();

        orderItemOptionGroupInfo.setId( orderItemOptionGroup.getId() );
        orderItemOptionGroupInfo.setOrdering( orderItemOptionGroup.getOrdering() );
        orderItemOptionGroupInfo.setItemOptionGroupName( orderItemOptionGroup.getItemOptionGroupName() );
        orderItemOptionGroupInfo.setOrderItemOptions( orderItemOptionListToOrderItemOptionInfoList( orderItemOptionGroup.getOrderItemOptions() ) );

        return orderItemOptionGroupInfo;
    }

    @Override
    public OrderCompletedMessage.OrderItemOptionInfo of(OrderItemOption orderItemOption) {
        if ( orderItemOption == null ) {
            return null;
        }

        OrderCompletedMessage.OrderItemOptionInfo orderItemOptionInfo = new OrderCompletedMessage.OrderItemOptionInfo();

        orderItemOptionInfo.setId( orderItemOption.getId() );
        orderItemOptionInfo.setOrdering( orderItemOption.getOrdering() );
        orderItemOptionInfo.setItemOptionName( orderItemOption.getItemOptionName() );

        return orderItemOptionInfo;
    }

    @Override
    public OrderUpdatedReceiverMessage of(Long orderId, OrderCommand.UpdateReceiverInfoCommand command) {
        if ( orderId == null && command == null ) {
            return null;
        }

        OrderUpdatedReceiverMessage orderUpdatedReceiverMessage = new OrderUpdatedReceiverMessage();

        if ( command != null ) {
            orderUpdatedReceiverMessage.setReceiverName( command.getReceiverName() );
            orderUpdatedReceiverMessage.setReceiverPhone( command.getReceiverPhone() );
            orderUpdatedReceiverMessage.setReceiverZipcode( command.getReceiverZipcode() );
            orderUpdatedReceiverMessage.setReceiverAddress1( command.getReceiverAddress1() );
            orderUpdatedReceiverMessage.setReceiverAddress2( command.getReceiverAddress2() );
            orderUpdatedReceiverMessage.setEtcMessage( command.getEtcMessage() );
        }
        orderUpdatedReceiverMessage.setOrderId( orderId );

        return orderUpdatedReceiverMessage;
    }

    protected OrderCompletedMessage.DeliveryInfo deliveryFragmentToDeliveryInfo(DeliveryFragment deliveryFragment) {
        if ( deliveryFragment == null ) {
            return null;
        }

        OrderCompletedMessage.DeliveryInfo deliveryInfo = new OrderCompletedMessage.DeliveryInfo();

        deliveryInfo.setReceiverName( deliveryFragment.getReceiverName() );
        deliveryInfo.setReceiverPhone( deliveryFragment.getReceiverPhone() );
        deliveryInfo.setReceiverZipcode( deliveryFragment.getReceiverZipcode() );
        deliveryInfo.setReceiverAddress1( deliveryFragment.getReceiverAddress1() );
        deliveryInfo.setReceiverAddress2( deliveryFragment.getReceiverAddress2() );
        deliveryInfo.setEtcMessage( deliveryFragment.getEtcMessage() );

        return deliveryInfo;
    }

    protected List<OrderCompletedMessage.OrderItemInfo> orderItemListToOrderItemInfoList(List<OrderItem> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderCompletedMessage.OrderItemInfo> list1 = new ArrayList<OrderCompletedMessage.OrderItemInfo>( list.size() );
        for ( OrderItem orderItem : list ) {
            list1.add( of( orderItem ) );
        }

        return list1;
    }

    protected List<OrderCompletedMessage.OrderItemOptionGroupInfo> orderItemOptionGroupListToOrderItemOptionGroupInfoList(List<OrderItemOptionGroup> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderCompletedMessage.OrderItemOptionGroupInfo> list1 = new ArrayList<OrderCompletedMessage.OrderItemOptionGroupInfo>( list.size() );
        for ( OrderItemOptionGroup orderItemOptionGroup : list ) {
            list1.add( of( orderItemOptionGroup ) );
        }

        return list1;
    }

    protected List<OrderCompletedMessage.OrderItemOptionInfo> orderItemOptionListToOrderItemOptionInfoList(List<OrderItemOption> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderCompletedMessage.OrderItemOptionInfo> list1 = new ArrayList<OrderCompletedMessage.OrderItemOptionInfo>( list.size() );
        for ( OrderItemOption orderItemOption : list ) {
            list1.add( of( orderItemOption ) );
        }

        return list1;
    }
}
