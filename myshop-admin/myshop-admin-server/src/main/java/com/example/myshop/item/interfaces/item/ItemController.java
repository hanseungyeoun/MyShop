package com.example.myshop.item.interfaces.item;

import com.example.myshop.item.application.item.ItemService;
import com.example.myshop.response.APIDataResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.myshop.item.interfaces.item.ItemDto.RegisterItemRequest;
import static com.example.myshop.item.interfaces.item.ItemDto.RegisterItemResponse;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final ItemDtoMapper itemDtoMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public APIDataResponse<RegisterItemResponse> registerItem(
            @RequestPart @Valid RegisterItemRequest itemInfo,
            @RequestPart(value = "itemImages") List<MultipartFile> itemImages
    ) {
        var itemCommand = itemDtoMapper.of(itemInfo, itemImages);
        var itemId = itemService.registerItem(itemCommand);
        var response = itemDtoMapper.of(itemId);
        return APIDataResponse.success(response);
    }

    @PutMapping("/{itemId}/change-on-sales")
    public APIDataResponse<String> changeOnSaleItem(@PathVariable Long itemId) {
        itemService.changeOnSaleItem(itemId);
        return APIDataResponse.success("OK");
    }

    @PutMapping("/{itemId}/change-end-of-sales")
    public APIDataResponse<String> changeEndOfSaleItem(@PathVariable Long itemId) {
        itemService.changeEndOfSaleItem(itemId);
        return APIDataResponse.success("OK");
    }

    @PostMapping(value = "/{itemId}/update", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public APIDataResponse<String> updateItem(
            @PathVariable Long itemId,
            @Valid @RequestPart ItemDto.UpdateItemRequest itemUpdateInfo,
            @RequestPart(value = "itemImages", required = true) List<MultipartFile> itemImages
    ) {
        var itemCommand = itemDtoMapper.of(itemUpdateInfo, itemImages);
        itemService.updateItem(itemId, itemCommand);
        return APIDataResponse.success("Ok");
    }
}
