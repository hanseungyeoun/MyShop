package com.example.myshop.item.interfaces.inventory;


import com.example.myshop.item.application.inventory.InventoryService;
import com.example.myshop.response.APIDataResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.example.myshop.item.interfaces.inventory.InventoryDto.RegisterInventoryResponse;
import static com.example.myshop.item.interfaces.inventory.InventoryDto.UpdateInventoryRequest;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private final InventoryDtoMapper inventoryDtoMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public APIDataResponse<RegisterInventoryResponse> registerStock(@RequestBody @Valid InventoryDto.RegisterInventoryRequest request) {
        var inventoryCommand = inventoryDtoMapper.of(request);
        var inventoryId = inventoryService.registerInventory(inventoryCommand);
        var response = inventoryDtoMapper.of(inventoryId);
        return APIDataResponse.success(response);
    }

    @PutMapping("/{inventoryId}/update")
    public APIDataResponse<String> updateStock( @PathVariable Long inventoryId, @RequestBody @Valid UpdateInventoryRequest request) {
        inventoryService.updateQuantity(inventoryId, request.getQuantity());
        return APIDataResponse.success("OK");
    }

    @DeleteMapping("/{inventoryId}")
    public APIDataResponse<String> deleteInventory(@RequestBody @Valid @PathVariable Long inventoryId) {
        inventoryService.deleteInventory(inventoryId);
        return APIDataResponse.success("OK");
    }
}
