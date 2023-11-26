package com.example.myshop.item.interfaces.item;

import com.example.myshop.item.application.item.ItemCommand;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-04T15:30:32+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class ItemDtoMapperImpl implements ItemDtoMapper {

    @Override
    public ItemCommand.RegisterItemCommand of(ItemDto.RegisterItemRequest request, List<MultipartFile> itemImages) {
        if ( request == null && itemImages == null ) {
            return null;
        }

        ItemCommand.RegisterItemCommand.RegisterItemCommandBuilder registerItemCommand = ItemCommand.RegisterItemCommand.builder();

        if ( request != null ) {
            registerItemCommand.itemPrice( ItemDtoMapper.integerToMoney( request.getItemPrice() ) );
            registerItemCommand.itemName( request.getItemName() );
            registerItemCommand.itemDetails( request.getItemDetails() );
            List<Long> list = request.getCategoryIds();
            if ( list != null ) {
                registerItemCommand.categoryIds( new ArrayList<Long>( list ) );
            }
            registerItemCommand.itemOptionGroups( registerItemOptionGroupRequestListToRegisterItemOptionGroupCommandList( request.getItemOptionGroups() ) );
        }
        List<MultipartFile> list2 = itemImages;
        if ( list2 != null ) {
            registerItemCommand.itemImages( new ArrayList<MultipartFile>( list2 ) );
        }

        return registerItemCommand.build();
    }

    @Override
    public ItemCommand.UpdateItemCommand of(ItemDto.UpdateItemRequest request, List<MultipartFile> itemImages) {
        if ( request == null && itemImages == null ) {
            return null;
        }

        ItemCommand.UpdateItemCommand.UpdateItemCommandBuilder updateItemCommand = ItemCommand.UpdateItemCommand.builder();

        if ( request != null ) {
            updateItemCommand.itemPrice( ItemDtoMapper.integerToMoney( request.getItemPrice() ) );
            updateItemCommand.itemName( request.getItemName() );
            updateItemCommand.itemDetails( request.getItemDetails() );
            List<Long> list = request.getCategoryIds();
            if ( list != null ) {
                updateItemCommand.categoryIds( new ArrayList<Long>( list ) );
            }
            updateItemCommand.status( request.getStatus() );
            updateItemCommand.itemOptionGroups( updateItemOptionGroupRequestListToUpdateItemOptionGroupCommandList( request.getItemOptionGroups() ) );
        }
        List<MultipartFile> list2 = itemImages;
        if ( list2 != null ) {
            updateItemCommand.itemImages( new ArrayList<MultipartFile>( list2 ) );
        }

        return updateItemCommand.build();
    }

    @Override
    public ItemDto.RegisterItemResponse of(Long id) {
        if ( id == null ) {
            return null;
        }

        ItemDto.RegisterItemResponse registerItemResponse = new ItemDto.RegisterItemResponse();

        registerItemResponse.setId( id );

        return registerItemResponse;
    }

    protected ItemCommand.RegisterItemOptionCommand registerItemOptionRequestToRegisterItemOptionCommand(ItemDto.RegisterItemOptionRequest registerItemOptionRequest) {
        if ( registerItemOptionRequest == null ) {
            return null;
        }

        ItemCommand.RegisterItemOptionCommand.RegisterItemOptionCommandBuilder registerItemOptionCommand = ItemCommand.RegisterItemOptionCommand.builder();

        registerItemOptionCommand.ordering( registerItemOptionRequest.getOrdering() );
        registerItemOptionCommand.itemOptionName( registerItemOptionRequest.getItemOptionName() );

        return registerItemOptionCommand.build();
    }

    protected List<ItemCommand.RegisterItemOptionCommand> registerItemOptionRequestListToRegisterItemOptionCommandList(List<ItemDto.RegisterItemOptionRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<ItemCommand.RegisterItemOptionCommand> list1 = new ArrayList<ItemCommand.RegisterItemOptionCommand>( list.size() );
        for ( ItemDto.RegisterItemOptionRequest registerItemOptionRequest : list ) {
            list1.add( registerItemOptionRequestToRegisterItemOptionCommand( registerItemOptionRequest ) );
        }

        return list1;
    }

    protected ItemCommand.RegisterItemOptionGroupCommand registerItemOptionGroupRequestToRegisterItemOptionGroupCommand(ItemDto.RegisterItemOptionGroupRequest registerItemOptionGroupRequest) {
        if ( registerItemOptionGroupRequest == null ) {
            return null;
        }

        ItemCommand.RegisterItemOptionGroupCommand.RegisterItemOptionGroupCommandBuilder registerItemOptionGroupCommand = ItemCommand.RegisterItemOptionGroupCommand.builder();

        registerItemOptionGroupCommand.ordering( registerItemOptionGroupRequest.getOrdering() );
        registerItemOptionGroupCommand.itemOptionGroupName( registerItemOptionGroupRequest.getItemOptionGroupName() );
        registerItemOptionGroupCommand.itemOptions( registerItemOptionRequestListToRegisterItemOptionCommandList( registerItemOptionGroupRequest.getItemOptions() ) );

        return registerItemOptionGroupCommand.build();
    }

    protected List<ItemCommand.RegisterItemOptionGroupCommand> registerItemOptionGroupRequestListToRegisterItemOptionGroupCommandList(List<ItemDto.RegisterItemOptionGroupRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<ItemCommand.RegisterItemOptionGroupCommand> list1 = new ArrayList<ItemCommand.RegisterItemOptionGroupCommand>( list.size() );
        for ( ItemDto.RegisterItemOptionGroupRequest registerItemOptionGroupRequest : list ) {
            list1.add( registerItemOptionGroupRequestToRegisterItemOptionGroupCommand( registerItemOptionGroupRequest ) );
        }

        return list1;
    }

    protected ItemCommand.UpdateItemOptionCommand registerItemOptionRequestToUpdateItemOptionCommand(ItemDto.RegisterItemOptionRequest registerItemOptionRequest) {
        if ( registerItemOptionRequest == null ) {
            return null;
        }

        ItemCommand.UpdateItemOptionCommand.UpdateItemOptionCommandBuilder updateItemOptionCommand = ItemCommand.UpdateItemOptionCommand.builder();

        updateItemOptionCommand.ordering( registerItemOptionRequest.getOrdering() );
        updateItemOptionCommand.itemOptionName( registerItemOptionRequest.getItemOptionName() );

        return updateItemOptionCommand.build();
    }

    protected List<ItemCommand.UpdateItemOptionCommand> registerItemOptionRequestListToUpdateItemOptionCommandList(List<ItemDto.RegisterItemOptionRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<ItemCommand.UpdateItemOptionCommand> list1 = new ArrayList<ItemCommand.UpdateItemOptionCommand>( list.size() );
        for ( ItemDto.RegisterItemOptionRequest registerItemOptionRequest : list ) {
            list1.add( registerItemOptionRequestToUpdateItemOptionCommand( registerItemOptionRequest ) );
        }

        return list1;
    }

    protected ItemCommand.UpdateItemOptionGroupCommand updateItemOptionGroupRequestToUpdateItemOptionGroupCommand(ItemDto.UpdateItemOptionGroupRequest updateItemOptionGroupRequest) {
        if ( updateItemOptionGroupRequest == null ) {
            return null;
        }

        ItemCommand.UpdateItemOptionGroupCommand.UpdateItemOptionGroupCommandBuilder updateItemOptionGroupCommand = ItemCommand.UpdateItemOptionGroupCommand.builder();

        updateItemOptionGroupCommand.ordering( updateItemOptionGroupRequest.getOrdering() );
        updateItemOptionGroupCommand.itemOptionGroupName( updateItemOptionGroupRequest.getItemOptionGroupName() );
        updateItemOptionGroupCommand.itemOptions( registerItemOptionRequestListToUpdateItemOptionCommandList( updateItemOptionGroupRequest.getItemOptions() ) );

        return updateItemOptionGroupCommand.build();
    }

    protected List<ItemCommand.UpdateItemOptionGroupCommand> updateItemOptionGroupRequestListToUpdateItemOptionGroupCommandList(List<ItemDto.UpdateItemOptionGroupRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<ItemCommand.UpdateItemOptionGroupCommand> list1 = new ArrayList<ItemCommand.UpdateItemOptionGroupCommand>( list.size() );
        for ( ItemDto.UpdateItemOptionGroupRequest updateItemOptionGroupRequest : list ) {
            list1.add( updateItemOptionGroupRequestToUpdateItemOptionGroupCommand( updateItemOptionGroupRequest ) );
        }

        return list1;
    }
}
