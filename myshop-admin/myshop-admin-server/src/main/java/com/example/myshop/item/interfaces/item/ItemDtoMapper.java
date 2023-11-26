package com.example.myshop.item.interfaces.item;

import com.example.myshop.common.jpa.Money;
import org.mapstruct.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.myshop.item.application.item.ItemCommand.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ItemDtoMapper {

    @Mapping(source = "request.itemPrice", target = "itemPrice", qualifiedByName = "integerToMoney")
    RegisterItemCommand of(ItemDto.RegisterItemRequest request, List<MultipartFile> itemImages);


    @Mapping(source = "request.itemPrice", target = "itemPrice", qualifiedByName = "integerToMoney")
    UpdateItemCommand of(ItemDto.UpdateItemRequest request, List<MultipartFile> itemImages);

    ItemDto.RegisterItemResponse of(Long id);

    @Named("integerToMoney")
    static Money integerToMoney(Integer itemPrice) {
        return itemPrice == null ? null : Money.valueOf(itemPrice);
    }

}
