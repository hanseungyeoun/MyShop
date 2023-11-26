package com.example.myshop.order.interfaces;

import com.example.myshop.order.domain.OrderInfo;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-04T14:54:20+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class OrderQueryDtoMapperImpl implements OrderQueryDtoMapper {

    private final DateTimeFormatter dateTimeFormatter_yyyy_MM_dd_HH_mm_ss_11333195168 = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" );

    @Override
    public OrderResponseDto.Main of(OrderInfo.Main mainResult) {
        if ( mainResult == null ) {
            return null;
        }

        OrderResponseDto.Main.MainBuilder main = OrderResponseDto.Main.builder();

        main.id( mainResult.getOrderId() );
        if ( mainResult.getOrderedAt() != null ) {
            main.orderedAt( dateTimeFormatter_yyyy_MM_dd_HH_mm_ss_11333195168.format( mainResult.getOrderedAt() ) );
        }
        main.payMethod( mainResult.getPayMethod() );
        main.totalAmount( mainResult.getTotalAmount() );
        main.deliveryInfo( deliveryInfoToDeliveryInfo( mainResult.getDeliveryInfo() ) );
        main.status( mainResult.getStatus() );
        main.statusDescription( mainResult.getStatusDescription() );
        main.orderItems( orderItemInfoListToOrderItemInfoList( mainResult.getOrderItems() ) );

        return main.build();
    }

    protected OrderResponseDto.DeliveryInfo deliveryInfoToDeliveryInfo(OrderInfo.DeliveryInfo deliveryInfo) {
        if ( deliveryInfo == null ) {
            return null;
        }

        OrderResponseDto.DeliveryInfo.DeliveryInfoBuilder deliveryInfo1 = OrderResponseDto.DeliveryInfo.builder();

        deliveryInfo1.receiverName( deliveryInfo.getReceiverName() );
        deliveryInfo1.receiverPhone( deliveryInfo.getReceiverPhone() );
        deliveryInfo1.receiverZipcode( deliveryInfo.getReceiverZipcode() );
        deliveryInfo1.receiverAddress1( deliveryInfo.getReceiverAddress1() );
        deliveryInfo1.receiverAddress2( deliveryInfo.getReceiverAddress2() );
        deliveryInfo1.etcMessage( deliveryInfo.getEtcMessage() );

        return deliveryInfo1.build();
    }

    protected OrderResponseDto.OrderItemOptionInfo orderItemOptionInfoToOrderItemOptionInfo(OrderInfo.OrderItemOptionInfo orderItemOptionInfo) {
        if ( orderItemOptionInfo == null ) {
            return null;
        }

        OrderResponseDto.OrderItemOptionInfo.OrderItemOptionInfoBuilder orderItemOptionInfo1 = OrderResponseDto.OrderItemOptionInfo.builder();

        orderItemOptionInfo1.ordering( orderItemOptionInfo.getOrdering() );
        orderItemOptionInfo1.itemOptionName( orderItemOptionInfo.getItemOptionName() );

        return orderItemOptionInfo1.build();
    }

    protected List<OrderResponseDto.OrderItemOptionInfo> orderItemOptionInfoListToOrderItemOptionInfoList(List<OrderInfo.OrderItemOptionInfo> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderResponseDto.OrderItemOptionInfo> list1 = new ArrayList<OrderResponseDto.OrderItemOptionInfo>( list.size() );
        for ( OrderInfo.OrderItemOptionInfo orderItemOptionInfo : list ) {
            list1.add( orderItemOptionInfoToOrderItemOptionInfo( orderItemOptionInfo ) );
        }

        return list1;
    }

    protected OrderResponseDto.OrderItemOptionGroup orderItemOptionGroupInfoToOrderItemOptionGroup(OrderInfo.OrderItemOptionGroupInfo orderItemOptionGroupInfo) {
        if ( orderItemOptionGroupInfo == null ) {
            return null;
        }

        OrderResponseDto.OrderItemOptionGroup.OrderItemOptionGroupBuilder orderItemOptionGroup = OrderResponseDto.OrderItemOptionGroup.builder();

        orderItemOptionGroup.ordering( orderItemOptionGroupInfo.getOrdering() );
        orderItemOptionGroup.itemOptionGroupName( orderItemOptionGroupInfo.getItemOptionGroupName() );
        orderItemOptionGroup.orderItemOptions( orderItemOptionInfoListToOrderItemOptionInfoList( orderItemOptionGroupInfo.getOrderItemOptions() ) );

        return orderItemOptionGroup.build();
    }

    protected List<OrderResponseDto.OrderItemOptionGroup> orderItemOptionGroupInfoListToOrderItemOptionGroupList(List<OrderInfo.OrderItemOptionGroupInfo> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderResponseDto.OrderItemOptionGroup> list1 = new ArrayList<OrderResponseDto.OrderItemOptionGroup>( list.size() );
        for ( OrderInfo.OrderItemOptionGroupInfo orderItemOptionGroupInfo : list ) {
            list1.add( orderItemOptionGroupInfoToOrderItemOptionGroup( orderItemOptionGroupInfo ) );
        }

        return list1;
    }

    protected OrderResponseDto.OrderItemInfo orderItemInfoToOrderItemInfo(OrderInfo.OrderItemInfo orderItemInfo) {
        if ( orderItemInfo == null ) {
            return null;
        }

        OrderResponseDto.OrderItemInfo.OrderItemInfoBuilder orderItemInfo1 = OrderResponseDto.OrderItemInfo.builder();

        orderItemInfo1.orderCount( orderItemInfo.getOrderCount() );
        orderItemInfo1.itemId( orderItemInfo.getItemId() );
        orderItemInfo1.itemName( orderItemInfo.getItemName() );
        orderItemInfo1.totalAmount( orderItemInfo.getTotalAmount() );
        if ( orderItemInfo.getItemPrice() != null ) {
            orderItemInfo1.itemPrice( orderItemInfo.getItemPrice().longValue() );
        }
        orderItemInfo1.deliveryStatus( orderItemInfo.getDeliveryStatus() );
        orderItemInfo1.deliveryStatusDescription( orderItemInfo.getDeliveryStatusDescription() );
        orderItemInfo1.orderItemOptionGroups( orderItemOptionGroupInfoListToOrderItemOptionGroupList( orderItemInfo.getOrderItemOptionGroups() ) );

        return orderItemInfo1.build();
    }

    protected List<OrderResponseDto.OrderItemInfo> orderItemInfoListToOrderItemInfoList(List<OrderInfo.OrderItemInfo> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderResponseDto.OrderItemInfo> list1 = new ArrayList<OrderResponseDto.OrderItemInfo>( list.size() );
        for ( OrderInfo.OrderItemInfo orderItemInfo : list ) {
            list1.add( orderItemInfoToOrderItemInfo( orderItemInfo ) );
        }

        return list1;
    }
}
