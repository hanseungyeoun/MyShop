package com.example.myshop.order.domain;

import com.example.myshop.coupon.domain.Issued.IssuedCoupon;
import com.example.myshop.exception.IllegalStatusException;
import com.example.myshop.order.infrastructure.OrderValidatorImpl;
import com.example.myshop.item.domain.inventory.Inventory;
import com.example.myshop.item.domain.inventory.InventoryReader;
import com.example.myshop.item.domain.item.Item;
import com.example.myshop.exception.InvalidParamException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.example.myshop.CoreFixtures.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class OrderValidatorImplTest {

    @InjectMocks
    private OrderValidatorImpl sut;

    @Mock
    private OrderReader orderReader;
    @Mock
    private InventoryReader inventoryReader;

    @Test
    void 주문항목이_빈경우_실패() {
        //given
        Order order = anOrder().orderItems(Collections.emptyList()).build();

        //when &then
        assertThatThrownBy(() -> {
            sut.validate(order, new HashMap<>(), new HashMap<>(), List.of());
        })
        .isInstanceOf(InvalidParamException.class)
        .hasMessageContaining("주문 항목이 비어 있습니다.");
    }

    @Test
    void 재고_부족_실패() {
        //given
        Item item = anItem().build();
        item.changeOnSale();

        HashMap<Long, Item> itemMap = new HashMap<>();
        itemMap.put(1L, item);

        HashMap<Long, Inventory> inventoryMap = new HashMap<>();
        inventoryMap.put(1L, aInventory().quantity(0).build());

        //when &then
        assertThatThrownBy(() -> {
            sut.validate(anOrder().build(), itemMap, inventoryMap,List.of());
        })
                .isInstanceOf(InvalidParamException.class)
                .hasMessageContaining("재고가 부족 합니다.");
    }

    @Test
    void 판매중이_아닌_상품_실패() {
        //given
        Item item = anItem().build();
        item.changeEndOfSale();

        HashMap<Long, Item> itemMap = new HashMap<>();
        itemMap.put(1L, item);

        HashMap<Long, Inventory> inventoryHashMap = new HashMap<>();
        inventoryHashMap.put(1L, aInventory().build());

        //when &then
        assertThatThrownBy(() -> {
            sut.validate(anOrder().build(), itemMap, inventoryHashMap, List.of());
        })
                .isInstanceOf(InvalidParamException.class)
                .hasMessageContaining("판매 중인 상품이 아닙니다.");
    }

    @Test
    void 메뉴옵션그룹이_변경된경우_실패() {
        //given
        Item item = anItem()
                .optionGroups(Collections.singletonList(aItemOptionGroup().itemOptionGroupName("그룹명").build()))
                .build();
        item.changeOnSale();


        Order order = anOrder().orderItems(Collections.singletonList(
                anOrderItem()
                    .orderItemOptionGroups(Collections.singletonList(anOrderOptionGroup().itemOptionGroupName("그룹명변경").build()))
                    .build()))
                .build();

        HashMap<Long, Item> itemMap = new HashMap<>();
        itemMap.put(1L, item);

        HashMap<Long, Inventory> inventoryMap = new HashMap<>();
        inventoryMap.put(1L, aInventory().quantity(0).build());

        //when &then
        assertThatThrownBy(() -> {
            sut.validate(order, itemMap, inventoryMap, List.of());
        })
                .isInstanceOf(InvalidParamException.class)
                .hasMessageContaining("옵션이 변경됐습니다.");
    }

    //
    @Test
    void 메뉴옵션이_변경된경우_실패() {
        //given
        Item item = anItem()
                .optionGroups(Collections.singletonList(
                    aItemOptionGroup()
                        .itemOptions(Collections.singletonList(aItemOption().itemOptionName("옵션").build()))
                        .build()))
                .build();
        item.changeOnSale();


        Order order = anOrder().orderItems(Collections.singletonList(
                anOrderItem()
                    .orderItemOptionGroups(Collections.singletonList(
                        anOrderOptionGroup()
                            .orderItemOptions(Collections.singletonList(anOrderOption().itemOptionName("옵션 변경").build()))
                            .build()))
                    .build()))
                        .build();


        HashMap<Long, Item> itemMap = new HashMap<>();
        itemMap.put(1L, item);

        HashMap<Long, Inventory> inventoryMap = new HashMap<>();
        inventoryMap.put(1L, aInventory().quantity(0).build());

        //when &then
        assertThatThrownBy(() -> {
            sut.validate(order, itemMap, inventoryMap, List.of());
        })
                .isInstanceOf(InvalidParamException.class)
                .hasMessageContaining("옵션이 변경됐습니다.");
    }

    @Test
    void 쿠폰이_유효하지_않을때_실패() {
        //given
        Item item = anItem().build();
        item.changeOnSale();

        HashMap<Long, Item> itemMap = new HashMap<>();
        itemMap.put(1L, item);

        HashMap<Long, Inventory> inventoryMap = new HashMap<>();
        inventoryMap.put(1L, aInventory().build());

        IssuedCoupon issuedCoupon = aIssueCoupon().build();
        Order order = anOrder()
                .orderItems(Collections.singletonList(
                        anOrderItem()
                                .issueCouponIds(Collections.singletonList(issuedCoupon.getId()))
                                .build()))
                .build();
        issuedCoupon.apply(order.getId());

        //Order order = anOrder().build();
        //when &then
        assertThatThrownBy(() -> {
            sut.validate(order, itemMap, inventoryMap, List.of(issuedCoupon));
        })
                .isInstanceOf(IllegalStatusException.class)
                .hasMessageContaining("이미 사용한 쿠폰입니다.");
    }
}