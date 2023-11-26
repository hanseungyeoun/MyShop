package com.example.myshop.order.application;

import com.example.myshop.IntegrationTest;
import com.example.myshop.common.jpa.Money;
import com.example.myshop.consumer.OrderCanceledMessageConsumer;
import com.example.myshop.consumer.OrderCompletedMessageConsumer;
import com.example.myshop.coupon.domain.coupon.AmountCoupon;
import com.example.myshop.coupon.domain.Issued.IssuedCoupon;
import com.example.myshop.coupon.domain.Issued.IssuedCouponStatus;
import com.example.myshop.coupon.infrastructure.coupon.CouponJpaRepository;
import com.example.myshop.coupon.infrastructure.Issuedcoupon.IssuedCouponJpaRepository;
import com.example.myshop.member.domain.Member;
import com.example.myshop.member.infrastructure.MemberJpaRepository;
import com.example.myshop.order.domain.Order;
import com.example.myshop.order.domain.OrderStatus;
import com.example.myshop.order.domain.payment.PayMethod;
import com.example.myshop.order.infrastructure.OrderJpaRepository;
import com.example.myshop.item.domain.category.Category;
import com.example.myshop.item.domain.category.CategoryStore;
import com.example.myshop.item.domain.inventory.Inventory;
import com.example.myshop.item.domain.item.Item;
import com.example.myshop.item.domain.item.ItemCategory;
import com.example.myshop.item.infrastructure.category.category.CategoryJpaRepository;
import com.example.myshop.item.infrastructure.inventory.InventoryJpaRepository;
import com.example.myshop.item.infrastructure.item.ItemJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static com.example.myshop.ApiFixtures.aPaymentCancelCommand;
import static com.example.myshop.ApiFixtures.aPaymentCommand;
import static com.example.myshop.CoreFixtures.*;
import static com.example.myshop.constant.Constant.CATEGORY_PATH_DELIMITER;
import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
@EmbeddedKafka(partitions = 3,
        brokerProperties = {
                "listeners=PLAINTEXT://localhost:9092"
        },
        ports = {9092})
public class OrderServiceKafkaTest {

    @Autowired
    OrderService sut;

    @Autowired
    ItemJpaRepository itemJpaRepository;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    CategoryJpaRepository categoryJpaRepository;

    @Autowired
    IssuedCouponJpaRepository issuedCouponJpaRepository;

    @Autowired
    CouponJpaRepository couponJpaRepository;

    @Autowired
    OrderCompletedMessageConsumer completedMessageConsumer;

    @Autowired
    OrderCanceledMessageConsumer orderCanceledMessageConsumer;

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Autowired
    CategoryStore categoryStore;

    @Autowired
    private InventoryJpaRepository inventoryJpaRepository;

    @AfterEach
    void afterEach() {
        memberJpaRepository.deleteAll();
        categoryJpaRepository.deleteAll();
        itemJpaRepository.deleteAll();

        inventoryJpaRepository.deleteAll();

        couponJpaRepository.deleteAll();
        issuedCouponJpaRepository.deleteAll();

        orderJpaRepository.deleteAll();

        completedMessageConsumer.resetLatch();
        orderCanceledMessageConsumer.resetLatch();
    }


    @Test
    void 결제() throws InterruptedException {
        //given
        int initQuantity = 10;
        int orderCount = 1;
        Member member = createMember();
        Item item = createItem(createCategory());
        Inventory inventory = createInventory(item.getId(), initQuantity);

        Order order = anOrder()
                .memberId(member.getId())
                .orderItems(Collections.singletonList(
                        anOrderItem()
                                .orderCount(orderCount)
                                .itemName(item.getItemName())
                                .memberId(member.getId())
                                .itemPrice(item.getItemPrice())
                                .itemId(item.getId())
                                .build()))
                .build();
        orderJpaRepository.save(order);

        OrderCommand.PaymentCommand paymentCommand = aPaymentCommand()
                .amount(Money.valueOf(9000))
                .payMethod(PayMethod.CARD)
                .build();

        //when
        sut.paymentOrder(order.getId(), paymentCommand);
        completedMessageConsumer.getLatch().await(2, TimeUnit.MINUTES);

        //then
        Order findOrder = orderJpaRepository.findById(order.getId()).get();
        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.ORDER_COMPLETE);

        Inventory findInventory = inventoryJpaRepository.findById(inventory.getId()).get();
        Integer quantity = findInventory.getQuantity();

        assertThat(quantity).isEqualTo(initQuantity - orderCount);
    }

    @Test
    void 결제_쿠폰() throws InterruptedException {
        //given
        int initQuantity = 10;
        int orderCount = 1;
        Member member = createMember();
        Item item = createItem(createCategory());
        Inventory inventory = createInventory(item.getId(), initQuantity);

        AmountCoupon coupon = anAmountCoupon()
                .itemIds(List.of(item.getId()))
                .build();
        couponJpaRepository.save(coupon);

        IssuedCoupon issuedCoupon = aIssueCoupon().memberId(member.getId()).couponId(coupon.getId()).build();
        issuedCouponJpaRepository.save(issuedCoupon);

        Order order = anOrder()
                .memberId(member.getId())
                .orderItems(Collections.singletonList(
                        anOrderItem()
                                .orderCount(orderCount)
                                .itemName(item.getItemName())
                                .memberId(member.getId())
                                .itemPrice(item.getItemPrice())
                                .orderCount(orderCount)
                                .itemId(item.getId())
                                .issueCouponIds(Collections.singletonList(issuedCoupon.getId()))
                                .build()))
                .build();
        orderJpaRepository.save(order);

        OrderCommand.PaymentCommand paymentCommand = aPaymentCommand()
                .amount(Money.valueOf(8100))
                .payMethod(PayMethod.CARD)
                .build();


        //when
        sut.paymentOrder(order.getId(), paymentCommand);

        //then
        completedMessageConsumer.getLatch().await(20, TimeUnit.SECONDS);

        //then
        Order findOrder = orderJpaRepository.findById(order.getId()).get();
        Inventory findInventory = inventoryJpaRepository.findById(inventory.getId()).get();
        IssuedCoupon findIssuedCoupon = issuedCouponJpaRepository.findById(issuedCoupon.getId()).get();

        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.ORDER_COMPLETE);
        assertThat(findInventory.getQuantity()).isEqualTo(initQuantity - orderCount);
        assertThat(findIssuedCoupon.getStatus()).isEqualTo(IssuedCouponStatus.USED);
    }

    @Test
    void 결제_동시_테스트() throws InterruptedException {
        //given
        int group_count = 2;
        int initQuantity = 10;
        int orderCount = 1;
        int size = 5;
        Member member = createMember();
        Item item = createItem(createCategory());
        Inventory inventory = createInventory(item.getId(), initQuantity);

        List<Order> orders = Stream.generate(() -> {
                    Order order = anOrder()
                            .memberId(member.getId())
                            .orderItems(Collections.singletonList(
                                    anOrderItem()
                                            .orderCount(orderCount)
                                            .itemName(item.getItemName())
                                            .memberId(member.getId())
                                            .itemPrice(item.getItemPrice())
                                            .itemId(item.getId())
                                            .build()
                            ))
                            .build();

                    orderJpaRepository.saveAndFlush(order);
                    return order;
                }).limit(size)
                .toList();

        OrderCommand.PaymentCommand paymentCommand = aPaymentCommand()
                .amount(Money.valueOf(9000))
                .payMethod(PayMethod.CARD)
                .build();

        //when
        completedMessageConsumer.resetLatch(orders.size()* group_count);
        final ExecutorService executor = Executors.newFixedThreadPool(orders.size());

        for (final Order order : orders) {
            executor.execute(() -> sut.paymentOrder(order.getId(), paymentCommand));
        }

        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);
        completedMessageConsumer.getLatch().await(30, TimeUnit.SECONDS);

        //then
        Inventory findInventory = inventoryJpaRepository.findById(inventory.getId()).get();
        Integer quantity = findInventory.getQuantity();
        assertThat(quantity).isEqualTo(initQuantity - (orderCount * orders.size()));
    }


    @Test
    void 주문_취소() throws InterruptedException {
        //given
        int initQuantity = 10;
        int orderCount = 1;
        Member member = createMember();
        Item item = createItem(createCategory());
        Inventory inventory = createInventory(item.getId(), initQuantity);

        Order order = anOrder()
                .memberId(member.getId())
                .orderItems(Collections.singletonList(
                        anOrderItem()
                                .orderCount(orderCount)
                                .itemName(item.getItemName())
                                .memberId(member.getId())
                                .itemPrice(item.getItemPrice())
                                .itemId(item.getId())
                                .build()
                ))
                .build();

        order.orderComplete();
        orderJpaRepository.save(order);

        OrderCommand.PaymentCancelCommand paymentCommand = aPaymentCancelCommand()
                .amount(Money.valueOf(9000)).build();

        //when
        sut.cancelPaymentOrder(order.getId(), paymentCommand);

        //then
        completedMessageConsumer.getLatch().await(20, TimeUnit.SECONDS);

        Order findOrder = orderJpaRepository.findById(order.getId()).get();
        Inventory findInventory = inventoryJpaRepository.findById(inventory.getId()).get();
        Integer quantity = findInventory.getQuantity();

        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.CANCELED);
        assertThat(quantity).isEqualTo(initQuantity + orderCount);
    }

    @Test
    void 주문_취소_쿠폰() throws InterruptedException {
        //given
        int initQuantity = 10;
        int orderCount = 1;
        Member member = createMember();
        Item item = createItem(createCategory());
        Inventory inventory = createInventory(item.getId(), initQuantity);
        AmountCoupon coupon = anAmountCoupon()
                .itemIds(List.of(item.getId()))
                .build();
        couponJpaRepository.save(coupon);

        IssuedCoupon issuedCoupon = aIssueCoupon().memberId(member.getId()).couponId(coupon.getId()).build();
        issuedCouponJpaRepository.save(issuedCoupon);

        Order order = anOrder()
                .memberId(member.getId())
                .orderItems(Collections.singletonList(
                        anOrderItem()
                                .orderCount(orderCount)
                                .itemName(item.getItemName())
                                .memberId(member.getId())
                                .itemPrice(item.getItemPrice())
                                .orderCount(orderCount)
                                .itemId(item.getId())
                                .issueCouponIds(Collections.singletonList(issuedCoupon.getId()))
                                .build()))
                .build();
        order.orderComplete();
        orderJpaRepository.save(order);

        OrderCommand.PaymentCancelCommand paymentCommand = aPaymentCancelCommand()
                .amount(Money.valueOf(8100)).build();

        //when
        sut.cancelPaymentOrder(order.getId(), paymentCommand);

        //then
        completedMessageConsumer.getLatch().await(20, TimeUnit.SECONDS);

        Order findOrder = orderJpaRepository.findById(order.getId()).get();
        Inventory findInventory = inventoryJpaRepository.findById(inventory.getId()).get();
        IssuedCoupon findIssuedCoupon = issuedCouponJpaRepository.findById(issuedCoupon.getId()).get();
        Integer quantity = findInventory.getQuantity();

        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.CANCELED);
        assertThat(quantity).isEqualTo(initQuantity + orderCount);
        assertThat(findIssuedCoupon.getStatus()).isEqualTo(IssuedCouponStatus.NOT_USED);
    }


    @Test
    void 동시_주문_취소() throws InterruptedException {
        //given
        int group_count = 2;

        int initQuantity = 10;
        int orderCount = 1;
        Member member = createMember();
        Item item = createItem(createCategory());
        Inventory inventory = createInventory(item.getId(), initQuantity);

        List<Order> orders = Stream.generate(() -> {
                    Order order = anOrder()
                            .memberId(member.getId())
                            .orderItems(Collections.singletonList(
                                    anOrderItem()
                                            .orderCount(orderCount)
                                            .itemName(item.getItemName())
                                            .memberId(member.getId())
                                            .itemPrice(item.getItemPrice())
                                            .itemId(item.getId())
                                            .build()
                            ))
                            .build();

                    order.orderComplete();
                    orderJpaRepository.saveAndFlush(order);
                    return order;
                }).limit(10)
                .toList();


        OrderCommand.PaymentCancelCommand paymentCommand = aPaymentCancelCommand()
                .amount(Money.valueOf(9000)).build();

        //when
        orderCanceledMessageConsumer.resetLatch(orders.size()*group_count);
        final ExecutorService executor = Executors.newFixedThreadPool(orders.size());

        for (final Order order : orders) {
            executor.execute(() -> sut.cancelPaymentOrder(order.getId(), paymentCommand));
        }

        executor.shutdown();

        //then
        orderCanceledMessageConsumer.getLatch().await(2, TimeUnit.MINUTES);

        Inventory findInventory = inventoryJpaRepository.findById(inventory.getId()).get();
        Integer quantity = findInventory.getQuantity();

        assertThat(quantity).isEqualTo(initQuantity+orders.size());
    }

    private Inventory createInventory(Long itemId) {
        Inventory inventory = aInventory().itemId(itemId).build();
        return inventoryJpaRepository.save(inventory);
    }

    private Inventory createInventory(Long itemId, int quantity) {
        Inventory inventory = aInventory().itemId(itemId).quantity(quantity).build();
        return inventoryJpaRepository.save(inventory);
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
}
