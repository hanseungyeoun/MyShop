package com.example.myshop.item.interfaces.inventory;

import com.example.myshop.item.domain.inventory.InventoryInfo;
import com.example.myshop.item.application.inventory.InventoryQueryService;
import com.example.myshop.response.APIDataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryQueryController {

    private final InventoryQueryService inventoryQueryService;
    private final InventoryResponseDtoMapper inventoryResponseDtoMapper;

    @GetMapping("/items/{itemId}")
    public APIDataResponse<List<InventoryResponseDto.Main>> retrieveStocks(@PathVariable Long itemId) {
        List<InventoryInfo.Main> inventoryResult = inventoryQueryService.retrieveInventoryListByItemId(itemId);
        List<InventoryResponseDto.Main> response = inventoryResult.stream().map(inventoryResponseDtoMapper::of).toList();
        return APIDataResponse.success(response);
    }
}
