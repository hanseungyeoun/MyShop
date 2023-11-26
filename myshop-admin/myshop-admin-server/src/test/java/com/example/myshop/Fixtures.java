package com.example.myshop;

import com.example.myshop.common.jpa.Money;
import com.example.myshop.item.application.item.ItemCommand.RegisterItemOptionCommand.RegisterItemOptionCommandBuilder;
import com.example.myshop.item.application.item.ItemCommand.RegisterItemOptionGroupCommand.RegisterItemOptionGroupCommandBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.myshop.item.application.inventory.InventoryCommand.*;
import static com.example.myshop.item.application.inventory.InventoryCommand.RegisterInventoryCommand.RegisterInventoryCommandBuilder;
import static com.example.myshop.item.application.inventory.InventoryCommand.RegisterInventoryOptionCommand.RegisterInventoryOptionCommandBuilder;
import static com.example.myshop.item.application.item.ItemCommand.*;
import static com.example.myshop.item.application.item.ItemCommand.UpdateItemOptionCommand.UpdateItemOptionCommandBuilder;
import static com.example.myshop.item.application.item.ItemCommand.UpdateItemOptionGroupCommand.UpdateItemOptionGroupCommandBuilder;

public class Fixtures {
    public static RegisterInventoryCommandBuilder aInventoryCommand() {
        return RegisterInventoryCommand.builder()
                .quantity(10)
                .itemId(1L)
                .inventoryItemOptionGroups(Arrays.asList(
                        aInventoryOptionGroupCommand()
                                .ordering(1)
                                .itemOptionGroupName("사이즈")
                                .inventoryItemOptions(Collections.singletonList(aInventoryOptionCommand().ordering(1).itemOptionName("large").build()))
                                .build(),
                        aInventoryOptionGroupCommand()
                                .ordering(1)
                                .itemOptionGroupName("컬러")
                                .inventoryItemOptions(Collections.singletonList(aInventoryOptionCommand().ordering(1).itemOptionName("blue").build()))
                                .build()
                ));
    }

    public static RegisterInventoryOptionGroupCommand.RegisterInventoryOptionGroupCommandBuilder aInventoryOptionGroupCommand() {
        return RegisterInventoryOptionGroupCommand.builder()
                .ordering(1)
                .itemOptionGroupName("사이즈")
                .inventoryItemOptions(Collections.singletonList(aInventoryOptionCommand().build()));
    }

    public static RegisterInventoryOptionCommandBuilder aInventoryOptionCommand() {
        return RegisterInventoryOptionCommand.builder().ordering(100).itemOptionName("large");
    }

    /*
     * 상품
     */
    public static RegisterItemCommand.RegisterItemCommandBuilder aItemCommand() {
        return RegisterItemCommand.builder()
                .itemName("이쁜 가방")
                .itemPrice(Money.valueOf(100))
                .itemDetails("가방이 이쁨")
                .categoryIds(List.of(1L))
                .itemImages(Collections.singletonList(createMockMultipartFile("가방사진.jpg", "itemImage")))
                .itemOptionGroups(Arrays.asList(
                        aItemOptionGroupCommand()
                                .ordering(1)
                                .itemOptionGroupName("사이즈")
                                .itemOptions(Collections.singletonList(aItemOptionCommand().ordering(1).itemOptionName("large").build()))
                                .build(),
                        aItemOptionGroupCommand()
                                .ordering(2)
                                .itemOptionGroupName("컬러")
                                .itemOptions(Collections.singletonList(aItemOptionCommand().ordering(1).itemOptionName("blue").build()))
                                .build()));
    }

    public static RegisterItemOptionGroupCommandBuilder aItemOptionGroupCommand() {
        return RegisterItemOptionGroupCommand.builder()
                .ordering(1)
                .itemOptionGroupName("사이즈")
                .itemOptions(Collections.singletonList(aItemOptionCommand().build()));
    }

    public static RegisterItemOptionCommandBuilder aItemOptionCommand() {
        return RegisterItemOptionCommand.builder().ordering(1).itemOptionName("large");
    }

    public static UpdateItemCommand.UpdateItemCommandBuilder aUpdateItemCommand() {
        return UpdateItemCommand.builder()
                .itemName("이쁜 가방")
                .itemPrice(Money.valueOf(100))
                .itemDetails("가방이 이쁨")
                .categoryIds(List.of(1L))
                .itemImages(Collections.singletonList(createMockMultipartFile("가방사진.jpg", "itemImage")))
                .itemOptionGroups(Arrays.asList(
                        aUpdateItemOptionGroupCommand()
                                .ordering(1)
                                .itemOptionGroupName("사이즈")
                                .itemOptions(Collections.singletonList(aUpdateItemOptionCommand().ordering(1).itemOptionName("large").build()))
                                .build(),
                        aUpdateItemOptionGroupCommand()
                                .ordering(2)
                                .itemOptionGroupName("컬러")
                                .itemOptions(Collections.singletonList(aUpdateItemOptionCommand().ordering(1).itemOptionName("blue").build()))
                                .build()));
    }

    public static UpdateItemOptionGroupCommandBuilder aUpdateItemOptionGroupCommand() {
        return UpdateItemOptionGroupCommand.builder()
                .ordering(1)
                .itemOptionGroupName("사이즈")
                .itemOptions(Collections.singletonList(aUpdateItemOptionCommand().build()));
    }

    public static UpdateItemOptionCommandBuilder aUpdateItemOptionCommand() {
        return UpdateItemOptionCommand.builder().ordering(1).itemOptionName("large");
    }


    //MockMultipartFile
    public static MockMultipartFile createMockMultipartFile(String imagePath, String name) {
        ClassPathResource resource = new ClassPathResource("images/image.jpg");
        InputStream inputStream = null;
        MockMultipartFile mockMultipartFile = null;
        try {
            inputStream = resource.getInputStream();
            mockMultipartFile = new MockMultipartFile(name,
                    resource.getFilename(),
                    MediaType.IMAGE_JPEG_VALUE,
                    inputStream);

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mockMultipartFile;
    }
}
