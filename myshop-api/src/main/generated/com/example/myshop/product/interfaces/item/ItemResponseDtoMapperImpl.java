package com.example.myshop.item.interfaces.item;

import com.example.myshop.item.domain.item.ItemInfo;
import com.example.myshop.item.dto.ItemSearchCondition;
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
public class ItemResponseDtoMapperImpl implements ItemResponseDtoMapper {

    @Override
    public ItemResponseDto.Main of(ItemInfo.Main mainResult) {
        if ( mainResult == null ) {
            return null;
        }

        ItemResponseDto.Main.MainBuilder main = ItemResponseDto.Main.builder();

        main.id( mainResult.getItemId() );
        main.itemName( mainResult.getItemName() );
        main.itemPrice( mainResult.getItemPrice() );
        main.itemDetails( mainResult.getItemDetails() );
        main.itemStatus( mainResult.getItemStatus() );
        main.itemStatusDescription( mainResult.getItemStatusDescription() );
        List<String> list = mainResult.getItemImages();
        if ( list != null ) {
            main.itemImages( new ArrayList<String>( list ) );
        }
        List<ItemInfo.CouponInfo> list1 = mainResult.getCouponInfos();
        if ( list1 != null ) {
            main.couponInfos( new ArrayList<ItemInfo.CouponInfo>( list1 ) );
        }
        List<ItemInfo.CategoryInfo> list2 = mainResult.getCategoryInfos();
        if ( list2 != null ) {
            main.categoryInfos( new ArrayList<ItemInfo.CategoryInfo>( list2 ) );
        }
        List<ItemInfo.ItemOptionGroupInfo> list3 = mainResult.getItemOptionGroups();
        if ( list3 != null ) {
            main.itemOptionGroups( new ArrayList<ItemInfo.ItemOptionGroupInfo>( list3 ) );
        }

        return main.build();
    }

    @Override
    public ItemSearchCondition of(ItemSearchCondition condition) {
        if ( condition == null ) {
            return null;
        }

        ItemSearchCondition.ItemSearchConditionBuilder itemSearchCondition = ItemSearchCondition.builder();

        itemSearchCondition.searchType( condition.getSearchType() );
        itemSearchCondition.searchValue( condition.getSearchValue() );
        itemSearchCondition.itemStatus( condition.getItemStatus() );
        itemSearchCondition.startDatetime( condition.getStartDatetime() );
        itemSearchCondition.endDatetime( condition.getEndDatetime() );

        return itemSearchCondition.build();
    }
}
