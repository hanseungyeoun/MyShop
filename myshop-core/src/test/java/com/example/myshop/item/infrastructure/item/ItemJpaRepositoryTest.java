package com.example.myshop.item.infrastructure.item;

import com.example.myshop.CoreTestConfiguration;
import com.example.myshop.InMemoryDBJpaTest;
import com.example.myshop.enums.ItemSearchType;
import com.example.myshop.common.jpa.Money;
import com.example.myshop.config.TestJpaConfig;
import com.example.myshop.coupon.infrastructure.coupon.CouponJpaRepository;
import com.example.myshop.item.domain.item.ItemInfo;
import com.example.myshop.item.domain.category.Category;
import com.example.myshop.item.domain.item.Item;
import com.example.myshop.item.domain.item.ItemCategory;
import com.example.myshop.item.domain.item.ItemOption;
import com.example.myshop.item.domain.item.ItemOptionGroup;
import com.example.myshop.item.infrastructure.category.category.CategoryJpaRepository;
import com.example.myshop.item.dto.ItemSearchCondition;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.List;

import static com.example.myshop.constant.Constant.CATEGORY_PATH_DELIMITER;
import static com.example.myshop.item.infrastructure.item.ItemStatus.END_OF_SALE;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

import static com.example.myshop.CoreFixtures.*;


@Import({TestJpaConfig.class})
@InMemoryDBJpaTest
@ContextConfiguration(classes = {CoreTestConfiguration.class})
class ItemJpaRepositoryTest {

    @Autowired
    ItemJpaRepository itemJpaRepository;

    @Autowired
    CategoryJpaRepository categoryJpaRepository;

    @Autowired
    ItemOptionGroupRepository itemOptionGroupRepository;

    @Autowired
    ItemOptionRepository itemOptionRepository;

    @Autowired
    private CouponJpaRepository couponJpaRepository;
    @Autowired
    EntityManager entityManager;

    private ItemCategory itemCategory1;
    private ItemCategory itemCategory2;

    @Test
    void save_test() {
        //givn & when
        List<Category> categories = createCategory();
        Item item = createItem(categories);

        //when
        Item findTest = itemJpaRepository.findById(item.getId()).get();
        assertThat(findTest.getId()).isEqualTo(findTest.getId());
    }

    @Test
    void findAll_test() {
        //givn
        List<Category> categories = createCategory();
        Item item = createItem(categories);

        //when
        ItemSearchCondition cond = ItemSearchCondition.builder()
                .searchType(ItemSearchType.ITEM_NAME)
                .searchValue(item.getItemName())
                .startDatetime(item.getCreatedAt().minusDays(10).toLocalDate())
                .endDatetime(item.getCreatedAt().plusDays(10).toLocalDate())
                .build();


        //then
        PageRequest request = PageRequest.of(0, 10);
        Page<ItemInfo.Main> items = itemJpaRepository.findItemAll(cond, request);
        System.out.println("items = " + items);
        System.out.println("items = " + items.getContent());
        assertThat(items.getContent()).hasSize(1);
    }

    @Test
    void findItemAllByCategoryPath_test() {
        //given
        List<Category> categories = createCategory();
        Item item = createItem(categories);

        ItemSearchCondition cond = ItemSearchCondition.builder()
                .searchType(ItemSearchType.CATEGORY_PATH)
                .searchValue(categories.get(categories.size()-1).getPath())
                .startDatetime(item.getCreatedAt().minusDays(10).toLocalDate())
                .endDatetime(item.getCreatedAt().plusDays(10).toLocalDate())
                .build();

        //when
        PageRequest request = PageRequest.of(0, 10);
        Sort sort = Sort.by(Sort.Direction.DESC, "itemName");
        Page<ItemInfo.Main> items = itemJpaRepository.findItemAll(cond, request);

        System.out.println("items = " + items.getContent());
        assertThat(items.getContent()).hasSize(1);
    }

    @Test
    void itemSeriesDeleteTest(){
        //givn
        List<Category> categories = createCategory();
        Item item = createItem(categories);

        //when
        item.clearItemSeries();
        entityManager.flush();
        entityManager.clear();

        //then
        Item findItem = itemJpaRepository.findById(item.getId()).get();
        assertThat(findItem.getItemOptionGroups()).isEmpty();
        assertThat(findItem.getItemImages()).isEmpty();
        assertThat(findItem.getItemCategories()).isEmpty();
    }

    @Test
    void itemSeriesUpdateTest(){
        //givn
        List<Category> categories = createCategory();
        Item item = createItem(categories);

        //when
        item.changeItem("더 비싼 가방", Money.valueOf(10000), "더 비싼 가방 정보", END_OF_SALE);
        item.clearItemSeries();

        ItemOption updateOption1 = aItemOption()
                .ordering(1)
                .itemOptionName("blue")
                .build();

        ItemOption updateOption2 = aItemOption()
                .ordering(1)
                .itemOptionName("red")
                .build();

        ItemOptionGroup updateOptionGroup = aItemOptionGroup()
                .ordering(1)
                .itemOptionGroupName("컬러")
                .itemOptions(Arrays.asList(updateOption1, updateOption2))
                .build();

        item.addItemOptionGroup(updateOptionGroup);
        entityManager.flush();
        entityManager.clear();

        //then
        Item findItem = itemJpaRepository.findById(item.getId()).get();
        assertThat(findItem.getItemOptionGroups()).extracting(ItemOptionGroup::getItemOptionGroupName)
                .containsExactly("컬러");

        assertThat(findItem.getItemOptionGroups().get(0))
                .extracting(ItemOptionGroup::getItemOptions, as(InstanceOfAssertFactories.COLLECTION))
                .hasSize(2)
                .extracting("itemOptionName")
                .containsExactly("blue", "red");
    }

    private Item createItem(List<Category> categories) {
        List<ItemCategory> itemCategoryList = categories.stream().map(category -> aItemCategory().categoryId(category.getId()).build()).toList();

        Item item = anItem().itemCategories(itemCategoryList).build();
        item.changeOnSale();
        return itemJpaRepository.save(item);
    }

    private List<Category> createCategory(){
        Category category1 = aPCategory().parentCategoryId(null).name("top").build();
        categoryJpaRepository.save(category1);
        Category category2 = aPCategory().parentCategoryId(category1.getId()).name("shirt").build();
        categoryJpaRepository.save(category2);

        category1.setPath(CATEGORY_PATH_DELIMITER, category1.getId());
        category2.setPath(category1.getPath(), category2.getId());
        return categoryJpaRepository.saveAll(List.of(category1, category2));
    }

}