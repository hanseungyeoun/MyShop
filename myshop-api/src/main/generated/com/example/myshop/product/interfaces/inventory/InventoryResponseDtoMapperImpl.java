package com.example.myshop.item.interfaces.inventory;

import com.example.myshop.item.domain.inventory.InventoryInfo;
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
public class InventoryResponseDtoMapperImpl implements InventoryResponseDtoMapper {

    @Override
    public InventoryResponseDto.Main of(InventoryInfo.Main mainResult) {
        if ( mainResult == null ) {
            return null;
        }

        InventoryResponseDto.Main.MainBuilder main = InventoryResponseDto.Main.builder();

        main.id( mainResult.getId() );
        main.itemId( mainResult.getItemId() );
        main.itemName( mainResult.getItemName() );
        main.inventoryItemOptionGroups( inventoryOptionGroupInfoListToInventoryOptionGroupInfoList( mainResult.getInventoryItemOptionGroups() ) );

        return main.build();
    }

    protected InventoryResponseDto.InventoryOptionGroupInfo inventoryOptionGroupInfoToInventoryOptionGroupInfo(InventoryInfo.InventoryOptionGroupInfo inventoryOptionGroupInfo) {
        if ( inventoryOptionGroupInfo == null ) {
            return null;
        }

        InventoryResponseDto.InventoryOptionGroupInfo.InventoryOptionGroupInfoBuilder inventoryOptionGroupInfo1 = InventoryResponseDto.InventoryOptionGroupInfo.builder();

        inventoryOptionGroupInfo1.ordering( inventoryOptionGroupInfo.getOrdering() );
        inventoryOptionGroupInfo1.itemOptionGroupName( inventoryOptionGroupInfo.getItemOptionGroupName() );
        List<InventoryInfo.InventoryOptionInfo> list = inventoryOptionGroupInfo.getInventoryItemOptions();
        if ( list != null ) {
            inventoryOptionGroupInfo1.inventoryItemOptions( new ArrayList<InventoryInfo.InventoryOptionInfo>( list ) );
        }

        return inventoryOptionGroupInfo1.build();
    }

    protected List<InventoryResponseDto.InventoryOptionGroupInfo> inventoryOptionGroupInfoListToInventoryOptionGroupInfoList(List<InventoryInfo.InventoryOptionGroupInfo> list) {
        if ( list == null ) {
            return null;
        }

        List<InventoryResponseDto.InventoryOptionGroupInfo> list1 = new ArrayList<InventoryResponseDto.InventoryOptionGroupInfo>( list.size() );
        for ( InventoryInfo.InventoryOptionGroupInfo inventoryOptionGroupInfo : list ) {
            list1.add( inventoryOptionGroupInfoToInventoryOptionGroupInfo( inventoryOptionGroupInfo ) );
        }

        return list1;
    }
}
