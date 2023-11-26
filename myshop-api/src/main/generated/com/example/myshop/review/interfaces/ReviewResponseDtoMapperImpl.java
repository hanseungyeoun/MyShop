package com.example.myshop.review.interfaces;

import com.example.myshop.order.interfaces.OrderResponseDto;
import com.example.myshop.review.domain.ReviewInfo;
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
public class ReviewResponseDtoMapperImpl implements ReviewResponseDtoMapper {

    @Override
    public ReviewResponseDto.Main of(ReviewInfo.Main mainResult) {
        if ( mainResult == null ) {
            return null;
        }

        ReviewResponseDto.Main.MainBuilder main = ReviewResponseDto.Main.builder();

        main.id( mainResult.getId() );
        main.itemId( mainResult.getItemId() );
        main.itemName( mainResult.getItemName() );
        main.orderId( mainResult.getOrderId() );
        main.reviewText( mainResult.getReviewText() );
        main.reviewGrade( mainResult.getReviewGrade() );
        List<String> list = mainResult.getItemImages();
        if ( list != null ) {
            main.itemImages( new ArrayList<String>( list ) );
        }
        List<String> list1 = mainResult.getReviewImages();
        if ( list1 != null ) {
            main.reviewImages( new ArrayList<String>( list1 ) );
        }
        main.orderItemOptionGroupInfos( orderItemOptionGroupInfoListToOrderItemOptionGroupInfoList( mainResult.getOrderItemOptionGroupInfos() ) );

        return main.build();
    }

    @Override
    public ReviewResponseDto.WriteAbleReviewResponse of(ReviewInfo.WriteAbleReviewInfo mainResult) {
        if ( mainResult == null ) {
            return null;
        }

        ReviewResponseDto.WriteAbleReviewResponse.WriteAbleReviewResponseBuilder writeAbleReviewResponse = ReviewResponseDto.WriteAbleReviewResponse.builder();

        writeAbleReviewResponse.itemId( mainResult.getItemId() );
        writeAbleReviewResponse.memberId( mainResult.getMemberId() );
        writeAbleReviewResponse.orderId( mainResult.getOrderId() );
        writeAbleReviewResponse.status( mainResult.getStatus() );
        writeAbleReviewResponse.orderItems( orderItemInfoListToOrderItemInfoList( mainResult.getOrderItems() ) );

        return writeAbleReviewResponse.build();
    }

    protected OrderResponseDto.OrderItemOptionInfo orderItemOptionInfoToOrderItemOptionInfo(ReviewInfo.OrderItemOptionInfo orderItemOptionInfo) {
        if ( orderItemOptionInfo == null ) {
            return null;
        }

        OrderResponseDto.OrderItemOptionInfo.OrderItemOptionInfoBuilder orderItemOptionInfo1 = OrderResponseDto.OrderItemOptionInfo.builder();

        orderItemOptionInfo1.ordering( orderItemOptionInfo.getOrdering() );
        orderItemOptionInfo1.itemOptionName( orderItemOptionInfo.getItemOptionName() );

        return orderItemOptionInfo1.build();
    }

    protected List<OrderResponseDto.OrderItemOptionInfo> orderItemOptionInfoListToOrderItemOptionInfoList(List<ReviewInfo.OrderItemOptionInfo> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderResponseDto.OrderItemOptionInfo> list1 = new ArrayList<OrderResponseDto.OrderItemOptionInfo>( list.size() );
        for ( ReviewInfo.OrderItemOptionInfo orderItemOptionInfo : list ) {
            list1.add( orderItemOptionInfoToOrderItemOptionInfo( orderItemOptionInfo ) );
        }

        return list1;
    }

    protected ReviewResponseDto.OrderItemOptionGroupInfo orderItemOptionGroupInfoToOrderItemOptionGroupInfo(ReviewInfo.OrderItemOptionGroupInfo orderItemOptionGroupInfo) {
        if ( orderItemOptionGroupInfo == null ) {
            return null;
        }

        ReviewResponseDto.OrderItemOptionGroupInfo.OrderItemOptionGroupInfoBuilder orderItemOptionGroupInfo1 = ReviewResponseDto.OrderItemOptionGroupInfo.builder();

        orderItemOptionGroupInfo1.ordering( orderItemOptionGroupInfo.getOrdering() );
        orderItemOptionGroupInfo1.itemOptionGroupName( orderItemOptionGroupInfo.getItemOptionGroupName() );
        orderItemOptionGroupInfo1.orderItemOptions( orderItemOptionInfoListToOrderItemOptionInfoList( orderItemOptionGroupInfo.getOrderItemOptions() ) );

        return orderItemOptionGroupInfo1.build();
    }

    protected List<ReviewResponseDto.OrderItemOptionGroupInfo> orderItemOptionGroupInfoListToOrderItemOptionGroupInfoList(List<ReviewInfo.OrderItemOptionGroupInfo> list) {
        if ( list == null ) {
            return null;
        }

        List<ReviewResponseDto.OrderItemOptionGroupInfo> list1 = new ArrayList<ReviewResponseDto.OrderItemOptionGroupInfo>( list.size() );
        for ( ReviewInfo.OrderItemOptionGroupInfo orderItemOptionGroupInfo : list ) {
            list1.add( orderItemOptionGroupInfoToOrderItemOptionGroupInfo( orderItemOptionGroupInfo ) );
        }

        return list1;
    }

    protected OrderResponseDto.OrderItemOptionGroup orderItemOptionGroupInfoToOrderItemOptionGroup(ReviewInfo.OrderItemOptionGroupInfo orderItemOptionGroupInfo) {
        if ( orderItemOptionGroupInfo == null ) {
            return null;
        }

        OrderResponseDto.OrderItemOptionGroup.OrderItemOptionGroupBuilder orderItemOptionGroup = OrderResponseDto.OrderItemOptionGroup.builder();

        orderItemOptionGroup.ordering( orderItemOptionGroupInfo.getOrdering() );
        orderItemOptionGroup.itemOptionGroupName( orderItemOptionGroupInfo.getItemOptionGroupName() );
        orderItemOptionGroup.orderItemOptions( orderItemOptionInfoListToOrderItemOptionInfoList( orderItemOptionGroupInfo.getOrderItemOptions() ) );

        return orderItemOptionGroup.build();
    }

    protected List<OrderResponseDto.OrderItemOptionGroup> orderItemOptionGroupInfoListToOrderItemOptionGroupList(List<ReviewInfo.OrderItemOptionGroupInfo> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderResponseDto.OrderItemOptionGroup> list1 = new ArrayList<OrderResponseDto.OrderItemOptionGroup>( list.size() );
        for ( ReviewInfo.OrderItemOptionGroupInfo orderItemOptionGroupInfo : list ) {
            list1.add( orderItemOptionGroupInfoToOrderItemOptionGroup( orderItemOptionGroupInfo ) );
        }

        return list1;
    }

    protected ReviewResponseDto.OrderItemInfo orderItemInfoToOrderItemInfo(ReviewInfo.OrderItemInfo orderItemInfo) {
        if ( orderItemInfo == null ) {
            return null;
        }

        ReviewResponseDto.OrderItemInfo.OrderItemInfoBuilder orderItemInfo1 = ReviewResponseDto.OrderItemInfo.builder();

        orderItemInfo1.orderCount( orderItemInfo.getOrderCount() );
        orderItemInfo1.itemId( orderItemInfo.getItemId() );
        orderItemInfo1.itemName( orderItemInfo.getItemName() );
        orderItemInfo1.totalAmount( orderItemInfo.getTotalAmount() );
        if ( orderItemInfo.getItemPrice() != null ) {
            orderItemInfo1.itemPrice( orderItemInfo.getItemPrice().longValue() );
        }
        orderItemInfo1.orderItemOptionGroups( orderItemOptionGroupInfoListToOrderItemOptionGroupList( orderItemInfo.getOrderItemOptionGroups() ) );

        return orderItemInfo1.build();
    }

    protected List<ReviewResponseDto.OrderItemInfo> orderItemInfoListToOrderItemInfoList(List<ReviewInfo.OrderItemInfo> list) {
        if ( list == null ) {
            return null;
        }

        List<ReviewResponseDto.OrderItemInfo> list1 = new ArrayList<ReviewResponseDto.OrderItemInfo>( list.size() );
        for ( ReviewInfo.OrderItemInfo orderItemInfo : list ) {
            list1.add( orderItemInfoToOrderItemInfo( orderItemInfo ) );
        }

        return list1;
    }
}
