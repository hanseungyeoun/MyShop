package com.example.myshop.order.interfaces;

import com.example.myshop.order.application.OrderCommand;
import com.example.myshop.order.domain.OrderInfo;
import java.time.format.DateTimeFormatter;
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
public class OrderDtoMapperImpl implements OrderDtoMapper {

    private final DateTimeFormatter dateTimeFormatter_yyyy_MM_dd_HH_mm_ss_11333195168 = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" );

    @Override
    public OrderCommand.RegisterOrderItemCommand of(OrderDto.RegisterOrderItemRequest request) {
        if ( request == null ) {
            return null;
        }

        OrderCommand.RegisterOrderItemCommand.RegisterOrderItemCommandBuilder registerOrderItemCommand = OrderCommand.RegisterOrderItemCommand.builder();

        registerOrderItemCommand.itemPrice( OrderDtoMapper.integerToMoney( request.getItemPrice() ) );
        registerOrderItemCommand.orderCount( request.getOrderCount() );
        registerOrderItemCommand.itemId( request.getItemId() );
        registerOrderItemCommand.itemName( request.getItemName() );
        registerOrderItemCommand.memberId( request.getMemberId() );
        registerOrderItemCommand.orderItemOptionGroups( registerOrderItemOptionGroupRequestListToRegisterOrderItemOptionGroupCommandList( request.getOrderItemOptionGroups() ) );
        List<Long> list1 = request.getIssueCouponIds();
        if ( list1 != null ) {
            registerOrderItemCommand.issueCouponIds( new ArrayList<Long>( list1 ) );
        }

        return registerOrderItemCommand.build();
    }

    @Override
    public OrderCommand.RegisterOrderCommand of(OrderDto.RegisterOrderRequest request) {
        if ( request == null ) {
            return null;
        }

        OrderCommand.RegisterOrderCommand.RegisterOrderCommandBuilder registerOrderCommand = OrderCommand.RegisterOrderCommand.builder();

        registerOrderCommand.memberId( request.getMemberId() );
        registerOrderCommand.payMethod( request.getPayMethod() );
        registerOrderCommand.receiverName( request.getReceiverName() );
        registerOrderCommand.receiverPhone( request.getReceiverPhone() );
        registerOrderCommand.receiverZipcode( request.getReceiverZipcode() );
        registerOrderCommand.receiverAddress1( request.getReceiverAddress1() );
        registerOrderCommand.receiverAddress2( request.getReceiverAddress2() );
        registerOrderCommand.etcMessage( request.getEtcMessage() );
        registerOrderCommand.orderItems( registerOrderItemRequestListToRegisterOrderItemCommandList( request.getOrderItems() ) );

        return registerOrderCommand.build();
    }

    @Override
    public OrderDto.RegisterOrderResponse of(Long id) {
        if ( id == null ) {
            return null;
        }

        OrderDto.RegisterOrderResponse.RegisterOrderResponseBuilder registerOrderResponse = OrderDto.RegisterOrderResponse.builder();

        registerOrderResponse.id( id );

        return registerOrderResponse.build();
    }

    @Override
    public OrderCommand.PaymentCommand of(OrderDto.PaymentRequest request) {
        if ( request == null ) {
            return null;
        }

        OrderCommand.PaymentCommand.PaymentCommandBuilder paymentCommand = OrderCommand.PaymentCommand.builder();

        paymentCommand.amount( OrderDtoMapper.integerToMoney( request.getAmount() ) );
        paymentCommand.payMethod( request.getPayMethod() );
        paymentCommand.orderDescription( request.getOrderDescription() );

        return paymentCommand.build();
    }

    @Override
    public OrderCommand.PaymentCancelCommand of(OrderDto.PaymentCancelRequest request) {
        if ( request == null ) {
            return null;
        }

        OrderCommand.PaymentCancelCommand.PaymentCancelCommandBuilder paymentCancelCommand = OrderCommand.PaymentCancelCommand.builder();

        paymentCancelCommand.amount( OrderDtoMapper.integerToMoney( request.getAmount() ) );
        paymentCancelCommand.payMethod( request.getPayMethod() );
        paymentCancelCommand.orderDescription( request.getOrderDescription() );

        return paymentCancelCommand.build();
    }

    @Override
    public OrderCommand.UpdateReceiverInfoCommand of(OrderDto.UpdateReceiverInfoRequest request) {
        if ( request == null ) {
            return null;
        }

        OrderCommand.UpdateReceiverInfoCommand.UpdateReceiverInfoCommandBuilder updateReceiverInfoCommand = OrderCommand.UpdateReceiverInfoCommand.builder();

        updateReceiverInfoCommand.receiverName( request.getReceiverName() );
        updateReceiverInfoCommand.receiverPhone( request.getReceiverPhone() );
        updateReceiverInfoCommand.receiverZipcode( request.getReceiverZipcode() );
        updateReceiverInfoCommand.receiverAddress1( request.getReceiverAddress1() );
        updateReceiverInfoCommand.receiverAddress2( request.getReceiverAddress2() );
        updateReceiverInfoCommand.etcMessage( request.getEtcMessage() );

        return updateReceiverInfoCommand.build();
    }

    @Override
    public OrderResponseDto.Main of(OrderInfo.Main mainResult) {
        if ( mainResult == null ) {
            return null;
        }

        OrderResponseDto.Main.MainBuilder main = OrderResponseDto.Main.builder();

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

    protected OrderCommand.RegisterOrderItemOptionCommand registerOrderItemOptionRequestToRegisterOrderItemOptionCommand(OrderDto.RegisterOrderItemOptionRequest registerOrderItemOptionRequest) {
        if ( registerOrderItemOptionRequest == null ) {
            return null;
        }

        OrderCommand.RegisterOrderItemOptionCommand.RegisterOrderItemOptionCommandBuilder registerOrderItemOptionCommand = OrderCommand.RegisterOrderItemOptionCommand.builder();

        registerOrderItemOptionCommand.ordering( registerOrderItemOptionRequest.getOrdering() );
        registerOrderItemOptionCommand.itemOptionName( registerOrderItemOptionRequest.getItemOptionName() );

        return registerOrderItemOptionCommand.build();
    }

    protected List<OrderCommand.RegisterOrderItemOptionCommand> registerOrderItemOptionRequestListToRegisterOrderItemOptionCommandList(List<OrderDto.RegisterOrderItemOptionRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderCommand.RegisterOrderItemOptionCommand> list1 = new ArrayList<OrderCommand.RegisterOrderItemOptionCommand>( list.size() );
        for ( OrderDto.RegisterOrderItemOptionRequest registerOrderItemOptionRequest : list ) {
            list1.add( registerOrderItemOptionRequestToRegisterOrderItemOptionCommand( registerOrderItemOptionRequest ) );
        }

        return list1;
    }

    protected OrderCommand.RegisterOrderItemOptionGroupCommand registerOrderItemOptionGroupRequestToRegisterOrderItemOptionGroupCommand(OrderDto.RegisterOrderItemOptionGroupRequest registerOrderItemOptionGroupRequest) {
        if ( registerOrderItemOptionGroupRequest == null ) {
            return null;
        }

        OrderCommand.RegisterOrderItemOptionGroupCommand.RegisterOrderItemOptionGroupCommandBuilder registerOrderItemOptionGroupCommand = OrderCommand.RegisterOrderItemOptionGroupCommand.builder();

        registerOrderItemOptionGroupCommand.ordering( registerOrderItemOptionGroupRequest.getOrdering() );
        registerOrderItemOptionGroupCommand.itemOptionGroupName( registerOrderItemOptionGroupRequest.getItemOptionGroupName() );
        registerOrderItemOptionGroupCommand.orderItemOptions( registerOrderItemOptionRequestListToRegisterOrderItemOptionCommandList( registerOrderItemOptionGroupRequest.getOrderItemOptions() ) );

        return registerOrderItemOptionGroupCommand.build();
    }

    protected List<OrderCommand.RegisterOrderItemOptionGroupCommand> registerOrderItemOptionGroupRequestListToRegisterOrderItemOptionGroupCommandList(List<OrderDto.RegisterOrderItemOptionGroupRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderCommand.RegisterOrderItemOptionGroupCommand> list1 = new ArrayList<OrderCommand.RegisterOrderItemOptionGroupCommand>( list.size() );
        for ( OrderDto.RegisterOrderItemOptionGroupRequest registerOrderItemOptionGroupRequest : list ) {
            list1.add( registerOrderItemOptionGroupRequestToRegisterOrderItemOptionGroupCommand( registerOrderItemOptionGroupRequest ) );
        }

        return list1;
    }

    protected List<OrderCommand.RegisterOrderItemCommand> registerOrderItemRequestListToRegisterOrderItemCommandList(List<OrderDto.RegisterOrderItemRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderCommand.RegisterOrderItemCommand> list1 = new ArrayList<OrderCommand.RegisterOrderItemCommand>( list.size() );
        for ( OrderDto.RegisterOrderItemRequest registerOrderItemRequest : list ) {
            list1.add( of( registerOrderItemRequest ) );
        }

        return list1;
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
