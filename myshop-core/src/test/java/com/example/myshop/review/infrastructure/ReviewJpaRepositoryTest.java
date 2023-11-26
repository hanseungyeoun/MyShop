package com.example.myshop.review.infrastructure;

import com.example.myshop.CoreTestConfiguration;
import com.example.myshop.InMemoryDBJpaTest;
import com.example.myshop.config.TestJpaConfig;
import com.example.myshop.member.infrastructure.MemberJpaRepository;
import com.example.myshop.item.domain.item.ItemCategory;
import com.example.myshop.item.infrastructure.item.ItemJpaRepository;
import com.example.myshop.review.domain.ReviewInfo;
import com.example.myshop.coupon.infrastructure.coupon.CouponJpaRepository;
import com.example.myshop.member.domain.Member;
import com.example.myshop.order.domain.Order;
import com.example.myshop.order.domain.item.OrderItem;
import com.example.myshop.order.infrastructure.OrderJpaRepository;
import com.example.myshop.item.domain.category.Category;
import com.example.myshop.item.domain.item.Item;
import com.example.myshop.item.infrastructure.category.category.CategoryJpaRepository;
import com.example.myshop.item.infrastructure.inventory.InventoryJpaRepository;
import com.example.myshop.review.domain.Review;
import com.example.myshop.review.domain.ReviewGrade;
import com.example.myshop.review.domain.ReviewImage;
import com.example.myshop.review.domain.ReviewInfo.WriteAbleReviewInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collections;
import java.util.List;

import static com.example.myshop.constant.Constant.CATEGORY_PATH_DELIMITER;
import static org.assertj.core.api.Assertions.assertThat;
import static com.example.myshop.CoreFixtures.*;


@Import({TestJpaConfig.class})
@InMemoryDBJpaTest
@ContextConfiguration(classes = {CoreTestConfiguration.class})
public class ReviewJpaRepositoryTest {

  @Autowired
  private CouponJpaRepository couponJpaRepository;

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
  InventoryJpaRepository inventoryJpaRepository;


  @Test
  void findWriteAbleReviewList_test() {
    //given
    Member member = createMember();
    List<Category> categories = createCategory();
    Item item = createItem(categories);
    Order order = createOrder(item, member);
    order.orderComplete();
    order.deliveryPrepare();
    order.inDelivery();
    order.deliveryComplete();
    order.purchaseConfirmation();

    Order order2 = createOrder(item, member);

    //when
    List<WriteAbleReviewInfo> reviewInfos = reviewJpaRepository.findWriteAbleReviewList(member.getId());

    //then
    System.out.println("reviewInfos = " + reviewInfos);
    assertThat(reviewInfos).hasSize(1);
  }

  @Test
  void findByIdItemIdIn_test() {
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
    Page<ReviewInfo.Main> reviews = reviewJpaRepository.findAllByItemId(item.getId(), PageRequest.of(0, 10));
    System.out.println("reviewInfos = " + reviews);

    //then
    assertThat(reviews.getContent()).hasSize(1);
  }


  @Test
  void findReviewById_test() {
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
    ReviewInfo.Main reviewInfo = reviewJpaRepository.findReviewById(review.getId());

    //then
    System.out.println("reviewInfos = " + reviewInfo);
    assertThat(reviewInfo.getReviewText()).isEqualTo("리뷰");
  }

  @Test
  void findAll_test() {
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


    List<Review> reviews = reviewJpaRepository.findAll();
    System.out.println("reviewInfos = " + reviews);
    assertThat(reviews).hasSize(1);
  }

  private Item createItem(List<Category> categories) {
    List<ItemCategory> itemCategoryList = categories.stream().map(category -> aItemCategory().categoryId(category.getId()).build()).toList();

    Item item = anItem().itemCategories(itemCategoryList).build();
    item.changeOnSale();
    return itemJpaRepository.save(item);
  }

  private List<Category> createCategory() {
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

  Order createOrder(Item item, Member member) {
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
