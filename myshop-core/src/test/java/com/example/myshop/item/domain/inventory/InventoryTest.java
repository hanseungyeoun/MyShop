package com.example.myshop.item.domain.inventory;

import com.example.myshop.CoreFixtures;
import com.example.myshop.exception.IllegalStatusException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class InventoryTest {

    @Test
    void 재고_변경_오류(){
        //given
        Inventory inventory = CoreFixtures.aInventory().build();
        inventory.changeQuantity(200);

        //when &then
        Assertions.assertThatThrownBy (() -> {
            inventory.changeQuantity(-1);
        }).isInstanceOf(IllegalStatusException.class);
    }

    @Test
    void 재고_변경(){
        //given
        Inventory inventory = CoreFixtures.aInventory().build();

        //when
        inventory.changeQuantity(200);

        //then
        Assertions.assertThat(inventory.getQuantity()).isEqualTo(200);
    }

    @Test
    void 재고_증가(){
        //given

        Inventory inventory = CoreFixtures.aInventory().quantity(10).build();

        //when
        inventory.increaseQuantity(1);

        //then
        Assertions.assertThat(inventory.getQuantity()).isEqualTo(11);
    }

    @Test
    void 재고_감소_오류(){
        //given
        Inventory inventory = CoreFixtures.aInventory().quantity(10).build();

        //when & then
        Assertions.assertThatThrownBy (() -> {
            inventory.decreaseQuantity(11);
        }).isInstanceOf(IllegalStatusException.class);
    }

    @Test
    void 재고_감소(){
        //given
        Inventory inventory = CoreFixtures.aInventory().quantity(10).build();

        //when
        inventory.decreaseQuantity(1);

        //then
        Assertions.assertThat(inventory.getQuantity()).isEqualTo(9);
    }
}