package com.example.myshop.item.application.item;

import com.example.myshop.item.domain.category.Category;
import com.example.myshop.item.domain.item.Item;
import com.example.myshop.item.domain.item.ItemInfo;
import com.example.myshop.item.domain.item.ItemOption;
import com.example.myshop.item.domain.item.ItemOptionGroup;
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
public class ItemInfoMapperImpl implements ItemInfoMapper {

    @Override
    public ItemInfo.Main of(Item item, List<ItemInfo.CategoryInfo> categoryInfos) {
        if ( item == null && categoryInfos == null ) {
            return null;
        }

        ItemInfo.Main main = new ItemInfo.Main();

        if ( item != null ) {
            main.setItemId( item.getId() );
            main.setItemImages( ItemInfoMapper.itemImageToStringList( item.getItemImages() ) );
            main.setItemPrice( ItemInfoMapper.moneyToInteger( item.getItemPrice() ) );
            main.setItemName( item.getItemName() );
            main.setItemDetails( item.getItemDetails() );
            main.setItemOptionGroups( itemOptionGroupListToItemOptionGroupInfoList( item.getItemOptionGroups() ) );
        }
        List<ItemInfo.CategoryInfo> list2 = categoryInfos;
        if ( list2 != null ) {
            main.setCategoryInfos( new ArrayList<ItemInfo.CategoryInfo>( list2 ) );
        }
        main.setItemStatus( item.getStatus().name() );
        main.setItemStatusDescription( item.getStatus().getDescription() );

        return main;
    }

    @Override
    public ItemInfo.ItemOptionGroupInfo of(ItemOptionGroup itemOptionGroup) {
        if ( itemOptionGroup == null ) {
            return null;
        }

        ItemInfo.ItemOptionGroupInfo itemOptionGroupInfo = new ItemInfo.ItemOptionGroupInfo();

        itemOptionGroupInfo.setItemId( itemOptionGroupItemId( itemOptionGroup ) );
        itemOptionGroupInfo.setOrdering( itemOptionGroup.getOrdering() );
        itemOptionGroupInfo.setItemOptionGroupName( itemOptionGroup.getItemOptionGroupName() );
        itemOptionGroupInfo.setItemOptions( itemOptionListToItemOptionInfoList( itemOptionGroup.getItemOptions() ) );

        return itemOptionGroupInfo;
    }

    @Override
    public ItemInfo.CategoryInfo of(Long itemId, Category category) {
        if ( itemId == null && category == null ) {
            return null;
        }

        ItemInfo.CategoryInfo categoryInfo = new ItemInfo.CategoryInfo();

        if ( category != null ) {
            categoryInfo.setParentCategoryId( category.getParentCategoryId() );
            categoryInfo.setPath( category.getPath() );
            categoryInfo.setName( category.getName() );
        }
        categoryInfo.setItemId( itemId );

        return categoryInfo;
    }

    protected List<ItemInfo.ItemOptionGroupInfo> itemOptionGroupListToItemOptionGroupInfoList(List<ItemOptionGroup> list) {
        if ( list == null ) {
            return null;
        }

        List<ItemInfo.ItemOptionGroupInfo> list1 = new ArrayList<ItemInfo.ItemOptionGroupInfo>( list.size() );
        for ( ItemOptionGroup itemOptionGroup : list ) {
            list1.add( of( itemOptionGroup ) );
        }

        return list1;
    }

    private Long itemOptionGroupItemId(ItemOptionGroup itemOptionGroup) {
        if ( itemOptionGroup == null ) {
            return null;
        }
        Item item = itemOptionGroup.getItem();
        if ( item == null ) {
            return null;
        }
        Long id = item.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected ItemInfo.ItemOptionInfo itemOptionToItemOptionInfo(ItemOption itemOption) {
        if ( itemOption == null ) {
            return null;
        }

        ItemInfo.ItemOptionInfo itemOptionInfo = new ItemInfo.ItemOptionInfo();

        itemOptionInfo.setOrdering( itemOption.getOrdering() );
        itemOptionInfo.setItemOptionName( itemOption.getItemOptionName() );

        return itemOptionInfo;
    }

    protected List<ItemInfo.ItemOptionInfo> itemOptionListToItemOptionInfoList(List<ItemOption> list) {
        if ( list == null ) {
            return null;
        }

        List<ItemInfo.ItemOptionInfo> list1 = new ArrayList<ItemInfo.ItemOptionInfo>( list.size() );
        for ( ItemOption itemOption : list ) {
            list1.add( itemOptionToItemOptionInfo( itemOption ) );
        }

        return list1;
    }
}
