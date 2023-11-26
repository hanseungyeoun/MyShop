package com.example.myshop.item.interfaces.item;

import com.example.myshop.item.domain.item.ItemInfo;
import com.example.myshop.item.application.item.ItemQueryService;
import com.example.myshop.item.dto.ItemSearchCondition;
import com.example.myshop.response.APIDataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemQueryController {

    private final ItemQueryService itemQueryServiceImpl;
    private final ItemResponseDtoMapper mapper;

    @GetMapping("")
    public APIDataResponse<Page<ItemResponseDto.Main>> retrieveItem(
            ItemSearchCondition condition,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        ItemSearchCondition itemSearchCondition = mapper.of(condition);

        Page<ItemInfo.Main> itemResults = itemQueryServiceImpl.retrieveItems(itemSearchCondition, pageable);
        Page<ItemResponseDto.Main> response = itemResults.map(mapper::of);
        return APIDataResponse.success(response);
    }

    @GetMapping("/{itemId}")
    public APIDataResponse<ItemResponseDto.Main> retrieveItems(@PathVariable Long itemId) {
        ItemInfo.Main itemResult = itemQueryServiceImpl.retrieveItem(itemId);
        ItemResponseDto.Main response = mapper.of(itemResult);
        return APIDataResponse.success(response);
    }
}
