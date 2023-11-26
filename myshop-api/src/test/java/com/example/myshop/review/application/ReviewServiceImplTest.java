package com.example.myshop.review.application;

import com.example.myshop.ApiFixtures;
import com.example.myshop.IntegrationTest;
import com.example.myshop.member.domain.Member;
import com.example.myshop.member.infrastructure.MemberJpaRepository;
import com.example.myshop.order.domain.Order;
import com.example.myshop.order.domain.item.OrderItem;
import com.example.myshop.order.infrastructure.OrderJpaRepository;
import com.example.myshop.item.domain.category.Category;
import com.example.myshop.item.domain.item.Item;
import com.example.myshop.item.domain.item.ItemCategory;

import com.example.myshop.item.infrastructure.category.category.CategoryJpaRepository;
import com.example.myshop.item.infrastructure.item.ItemJpaRepository;
import com.example.myshop.review.domain.*;
import com.example.myshop.review.infrastructure.ReviewJpaRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.example.myshop.constant.Constant.CATEGORY_PATH_DELIMITER;
import static com.example.myshop.review.application.ReviewCommand.*;
import static org.assertj.core.api.Assertions.assertThat;
import static com.example.myshop.CoreFixtures.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@IntegrationTest
class ReviewServiceImplTest {

    @Autowired
    ReviewService sut;

    @Autowired
    ReviewJpaRepository reviewJpaRepository;

    @Autowired
    OrderJpaRepository orderJpaRepository;

    @Autowired
    ItemJpaRepository itemJpaRepository;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    CategoryJpaRepository categoryJpaRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void register_test() {
        //given
        Member member = createMember();
        List<Category> categories = createCategory();
        Item item = createItem(categories);
        Order order = createOrder(item, member);
        order.orderComplete();
        order.deliveryPrepare();
        order.inDelivery();
        order.deliveryComplete();

        RegisterReviewCommand command = ApiFixtures.aReviewCommand()
                .orderId(order.getId())
                .itemId(item.getId())
                .memberId(member.getId())
                .build();

        Long reviewId = sut.registerReview(command);

        //when
        Review findReview = reviewJpaRepository.findById(reviewId).get();

        //then
        System.out.println("findReview = " + findReview);
        assertThat(findReview.getId()).isEqualTo(reviewId);
        assertThat(findReview.getReviewText()).isEqualTo(command.getReviewText());
    }

    @Test
    void review_update_test() {
        //given
        Member member = createMember();
        List<Category> categories = createCategory();
        Item item = createItem(categories);
        Order order = createOrder(item, member);

        Review review = Review.builder()
                .memberId(member.getId())
                .orderId(order.getId())
                .itemId(item.getId())
                .reviewText("리뷰")
                .reviewGrade(ReviewGrade.ONE)
                .reviewImages(List.of(new ReviewImage("image.jpg")))
                .build();
        reviewJpaRepository.save(review);

        UpdateReviewCommand command = ApiFixtures.aUpdateReviewCommand().build();
        sut.updateReview(review.getId(), command);

        //when
        Review findReview = reviewJpaRepository.findById(review.getId()).get();

        //then
        assertThat(findReview.getReviewText()).isEqualTo("너무 너무 옷이 좋아요");
        assertThat(findReview.getReviewImages()).hasSize(1);
    }

    @Test
    void review_delete_test() {
        //given
        Member member = createMember();
        List<Category> categories = createCategory();
        Item item = createItem(categories);
        Order order = createOrder(item, member);

        Review review = Review.builder()
                .memberId(member.getId())
                .orderId(order.getId())
                .itemId(item.getId())
                .reviewText("리뷰")
                .reviewGrade(ReviewGrade.ONE)
                .reviewImages(List.of(new ReviewImage("image.jpg")))
                .build();

        reviewJpaRepository.save(review);


        //when
        sut.deleteReview(review.getId());
        entityManager.flush();
        entityManager.clear();

        //then
        assertThat(reviewJpaRepository.findAll()).hasSize(0);
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

    private Member createMember() {
        return memberJpaRepository.save(aMember().build());
    }

    private Order createOrder(Item item, Member member){
        OrderItem orderItem = anOrderItem()
                .orderCount(1)
                .itemId(item.getId())
                .itemName(item.getItemName())
                .memberId(member.getId())
                .itemPrice(item.getItemPrice())
                .build();

        Order order = anOrder()
                .memberId(member.getId())
                .orderItems(Collections.singletonList(
                    anOrderItem()
                        .itemName(item.getItemName())
                        .memberId(member.getId())
                        .itemPrice(item.getItemPrice())
                        .itemId(item.getId())
                        .build()))
                .build();
        return orderJpaRepository.save(order);
    }
}