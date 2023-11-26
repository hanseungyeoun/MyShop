package com.example.myshop.item.interfaces.inventory;

import com.example.myshop.item.application.inventory.InventoryCommand;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-04T15:30:32+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class InventoryDtoMapperImpl implements InventoryDtoMapper {

    @Override
    public InventoryCommand.RegisterInventoryCommand of(InventoryDto.RegisterInventoryRequest request) {
        if ( request == null ) {
            return null;
        }

        InventoryCommand.RegisterInventoryCommand.RegisterInventoryCommandBuilder registerInventoryCommand = InventoryCommand.RegisterInventoryCommand.builder();

        registerInventoryCommand.quantity( request.getQuantity() );
        registerInventoryCommand.itemId( request.getItemId() );
        registerInventoryCommand.inventoryItemOptionGroups( registerInventoryOptionGroupRequestListToRegisterInventoryOptionGroupCommandList( request.getInventoryItemOptionGroups() ) );

        return registerInventoryCommand.build();
    }

    @Override
    public InventoryDto.RegisterInventoryResponse of(Long id) {
        if ( id == null ) {
            return null;
        }

        InventoryDto.RegisterInventoryResponse registerInventoryResponse = new InventoryDto.RegisterInventoryResponse();

        registerInventoryResponse.setId( id );

        return registerInventoryResponse;
    }

    protected InventoryCommand.RegisterInventoryOptionCommand registerInventoryOptionRequestToRegisterInventoryOptionCommand(InventoryDto.RegisterInventoryOptionRequest registerInventoryOptionRequest) {
        if ( registerInventoryOptionRequest == null ) {
            return null;
        }

        InventoryCommand.RegisterInventoryOptionCommand.RegisterInventoryOptionCommandBuilder registerInventoryOptionCommand = InventoryCommand.RegisterInventoryOptionCommand.builder();

        registerInventoryOptionCommand.ordering( registerInventoryOptionRequest.getOrdering() );
        registerInventoryOptionCommand.itemOptionName( registerInventoryOptionRequest.getItemOptionName() );

        return registerInventoryOptionCommand.build();
    }

    protected List<InventoryCommand.RegisterInventoryOptionCommand> registerInventoryOptionRequestListToRegisterInventoryOptionCommandList(List<InventoryDto.RegisterInventoryOptionRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<InventoryCommand.RegisterInventoryOptionCommand> list1 = new ArrayList<InventoryCommand.RegisterInventoryOptionCommand>( list.size() );
        for ( InventoryDto.RegisterInventoryOptionRequest registerInventoryOptionRequest : list ) {
            list1.add( registerInventoryOptionRequestToRegisterInventoryOptionCommand( registerInventoryOptionRequest ) );
        }

        return list1;
    }

    protected InventoryCommand.RegisterInventoryOptionGroupCommand registerInventoryOptionGroupRequestToRegisterInventoryOptionGroupCommand(InventoryDto.RegisterInventoryOptionGroupRequest registerInventoryOptionGroupRequest) {
        if ( registerInventoryOptionGroupRequest == null ) {
            return null;
        }

        InventoryCommand.RegisterInventoryOptionGroupCommand.RegisterInventoryOptionGroupCommandBuilder registerInventoryOptionGroupCommand = InventoryCommand.RegisterInventoryOptionGroupCommand.builder();

        registerInventoryOptionGroupCommand.ordering( registerInventoryOptionGroupRequest.getOrdering() );
        registerInventoryOptionGroupCommand.itemOptionGroupName( registerInventoryOptionGroupRequest.getItemOptionGroupName() );
        registerInventoryOptionGroupCommand.inventoryItemOptions( registerInventoryOptionRequestListToRegisterInventoryOptionCommandList( registerInventoryOptionGroupRequest.getInventoryItemOptions() ) );

        return registerInventoryOptionGroupCommand.build();
    }

    protected List<InventoryCommand.RegisterInventoryOptionGroupCommand> registerInventoryOptionGroupRequestListToRegisterInventoryOptionGroupCommandList(List<InventoryDto.RegisterInventoryOptionGroupRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<InventoryCommand.RegisterInventoryOptionGroupCommand> list1 = new ArrayList<InventoryCommand.RegisterInventoryOptionGroupCommand>( list.size() );
        for ( InventoryDto.RegisterInventoryOptionGroupRequest registerInventoryOptionGroupRequest : list ) {
            list1.add( registerInventoryOptionGroupRequestToRegisterInventoryOptionGroupCommand( registerInventoryOptionGroupRequest ) );
        }

        return list1;
    }
}
