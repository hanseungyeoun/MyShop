package com.example.myshop.order.application;

import com.example.myshop.order.domain.Order;
import com.example.myshop.order.domain.OrderInfo;
import com.example.myshop.order.domain.fragment.DeliveryFragment;
import com.example.myshop.order.domain.item.OrderItem;
import com.example.myshop.order.domain.item.OrderItemOption;
import com.example.myshop.order.domain.item.OrderItemOptionGroup;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-04T14:54:19+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class OrderInfoMapperImpl implements OrderInfoMapper {

    @Override
    public OrderInfo.Main of(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderInfo.Main main = new OrderInfo.Main();

        main.setOrderId( order.getId() );
        main.setDeliveryInfo( deliveryFragmentToDeliveryInfo( order.getDeliveryFragment() ) );
        main.setTotalAmount( OrderInfoMapper.moneyToInteger( order.getTotalAmount() ) );
        main.setMemberId( order.getMemberId() );
        main.setPayMethod( order.getPayMethod() );
        main.setOrderedAt( order.getOrderedAt() );
        main.setOrderItems( orderItemListToOrderItemInfoList( order.getOrderItems() ) );

        main.setStatus( order.getStatus().name() );
        main.setStatusDescription( order.getStatus().getDescription() );

        return main;
    }

    @Override
    public OrderInfo.OrderItemInfo of(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }

        OrderInfo.OrderItemInfo orderItemInfo = new OrderInfo.OrderItemInfo();

        orderItemInfo.setOrderItemId( orderItem.getId() );
        orderItemInfo.setItemPrice( OrderInfoMapper.moneyToInteger( orderItem.getItemPrice() ) );
        orderItemInfo.setTotalAmount( OrderInfoMapper.moneyToInteger( orderItem.getTotalAmount() ) );
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
    public OrderInfo.OrderItemOptionGroupInfo of(OrderItemOptionGroup orderItemOptionGroup) {
        if ( orderItemOptionGroup == null ) {
            return null;
        }

        OrderInfo.OrderItemOptionGroupInfo orderItemOptionGroupInfo = new OrderInfo.OrderItemOptionGroupInfo();

        orderItemOptionGroupInfo.setOptionGroupId( orderItemOptionGroup.getId() );
        orderItemOptionGroupInfo.setOrdering( orderItemOptionGroup.getOrdering() );
        orderItemOptionGroupInfo.setItemOptionGroupName( orderItemOptionGroup.getItemOptionGroupName() );
        orderItemOptionGroupInfo.setOrderItemOptions( orderItemOptionListToOrderItemOptionInfoList( orderItemOptionGroup.getOrderItemOptions() ) );

        return orderItemOptionGroupInfo;
    }

    @Override
    public OrderInfo.OrderItemOptionInfo of(OrderItemOption orderItemOption) {
        if ( orderItemOption == null ) {
            return null;
        }

        OrderInfo.OrderItemOptionInfo orderItemOptionInfo = new OrderInfo.OrderItemOptionInfo();

        orderItemOptionInfo.setOptionId( orderItemOption.getId() );
        orderItemOptionInfo.setOrdering( orderItemOption.getOrdering() );
        orderItemOptionInfo.setItemOptionName( orderItemOption.getItemOptionName() );

        return orderItemOptionInfo;
    }

    protected OrderInfo.DeliveryInfo deliveryFragmentToDeliveryInfo(DeliveryFragment deliveryFragment) {
        if ( deliveryFragment == null ) {
            return null;
        }

        OrderInfo.DeliveryInfo.DeliveryInfoBuilder deliveryInfo = OrderInfo.DeliveryInfo.builder();

        deliveryInfo.receiverName( deliveryFragment.getReceiverName() );
        deliveryInfo.receiverPhone( deliveryFragment.getReceiverPhone() );
        deliveryInfo.receiverZipcode( deliveryFragment.getReceiverZipcode() );
        deliveryInfo.receiverAddress1( deliveryFragment.getReceiverAddress1() );
        deliveryInfo.receiverAddress2( deliveryFragment.getReceiverAddress2() );
        deliveryInfo.etcMessage( deliveryFragment.getEtcMessage() );

        return deliveryInfo.build();
    }

    protected List<OrderInfo.OrderItemInfo> orderItemListToOrderItemInfoList(List<OrderItem> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderInfo.OrderItemInfo> list1 = new ArrayList<OrderInfo.OrderItemInfo>( list.size() );
        for ( OrderItem orderItem : list ) {
            list1.add( of( orderItem ) );
        }

        return list1;
    }

    protected List<OrderInfo.OrderItemOptionGroupInfo> orderItemOptionGroupListToOrderItemOptionGroupInfoList(List<OrderItemOptionGroup> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderInfo.OrderItemOptionGroupInfo> list1 = new ArrayList<OrderInfo.OrderItemOptionGroupInfo>( list.size() );
        for ( OrderItemOptionGroup orderItemOptionGroup : list ) {
            list1.add( of( orderItemOptionGroup ) );
        }

        return list1;
    }

    protected List<OrderInfo.OrderItemOptionInfo> orderItemOptionListToOrderItemOptionInfoList(List<OrderItemOption> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderInfo.OrderItemOptionInfo> list1 = new ArrayList<OrderInfo.OrderItemOptionInfo>( list.size() );
        for ( OrderItemOption orderItemOption : list ) {
            list1.add( of( orderItemOption ) );
        }

        return list1;
    }
}
