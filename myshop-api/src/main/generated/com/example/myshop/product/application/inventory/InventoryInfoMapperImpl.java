package com.example.myshop.item.application.inventory;

import com.example.myshop.item.domain.inventory.Inventory;
import com.example.myshop.item.domain.inventory.InventoryInfo;
import com.example.myshop.item.domain.inventory.InventoryOption;
import com.example.myshop.item.domain.inventory.InventoryOptionGroup;
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
public class InventoryInfoMapperImpl implements InventoryInfoMapper {

    @Override
    public InventoryInfo.Main of(Inventory stock) {
        if ( stock == null ) {
            return null;
        }

        InventoryInfo.Main.MainBuilder main = InventoryInfo.Main.builder();

        main.id( stock.getId() );
        main.quantity( stock.getQuantity() );
        main.itemId( stock.getItemId() );
        main.inventoryItemOptionGroups( inventoryOptionGroupListToInventoryOptionGroupInfoList( stock.getInventoryItemOptionGroups() ) );

        return main.build();
    }

    protected InventoryInfo.InventoryOptionInfo inventoryOptionToInventoryOptionInfo(InventoryOption inventoryOption) {
        if ( inventoryOption == null ) {
            return null;
        }

        InventoryInfo.InventoryOptionInfo.InventoryOptionInfoBuilder inventoryOptionInfo = InventoryInfo.InventoryOptionInfo.builder();

        inventoryOptionInfo.ordering( inventoryOption.getOrdering() );
        inventoryOptionInfo.itemOptionName( inventoryOption.getItemOptionName() );

        return inventoryOptionInfo.build();
    }

    protected List<InventoryInfo.InventoryOptionInfo> inventoryOptionListToInventoryOptionInfoList(List<InventoryOption> list) {
        if ( list == null ) {
            return null;
        }

        List<InventoryInfo.InventoryOptionInfo> list1 = new ArrayList<InventoryInfo.InventoryOptionInfo>( list.size() );
        for ( InventoryOption inventoryOption : list ) {
            list1.add( inventoryOptionToInventoryOptionInfo( inventoryOption ) );
        }

        return list1;
    }

    protected InventoryInfo.InventoryOptionGroupInfo inventoryOptionGroupToInventoryOptionGroupInfo(InventoryOptionGroup inventoryOptionGroup) {
        if ( inventoryOptionGroup == null ) {
            return null;
        }

        InventoryInfo.InventoryOptionGroupInfo.InventoryOptionGroupInfoBuilder inventoryOptionGroupInfo = InventoryInfo.InventoryOptionGroupInfo.builder();

        inventoryOptionGroupInfo.ordering( inventoryOptionGroup.getOrdering() );
        inventoryOptionGroupInfo.itemOptionGroupName( inventoryOptionGroup.getItemOptionGroupName() );
        inventoryOptionGroupInfo.inventoryItemOptions( inventoryOptionListToInventoryOptionInfoList( inventoryOptionGroup.getInventoryItemOptions() ) );

        return inventoryOptionGroupInfo.build();
    }

    protected List<InventoryInfo.InventoryOptionGroupInfo> inventoryOptionGroupListToInventoryOptionGroupInfoList(List<InventoryOptionGroup> list) {
        if ( list == null ) {
            return null;
        }

        List<InventoryInfo.InventoryOptionGroupInfo> list1 = new ArrayList<InventoryInfo.InventoryOptionGroupInfo>( list.size() );
        for ( InventoryOptionGroup inventoryOptionGroup : list ) {
            list1.add( inventoryOptionGroupToInventoryOptionGroupInfo( inventoryOptionGroup ) );
        }

        return list1;
    }
}
