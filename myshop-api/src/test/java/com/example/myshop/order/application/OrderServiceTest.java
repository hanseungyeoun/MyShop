package com.example.myshop.order.application;

import com.example.myshop.IntegrationTest;
import com.example.myshop.common.jpa.Money;
import com.example.myshop.consumer.OrderCanceledMessageConsumer;
import com.example.myshop.consumer.OrderCompletedMessageConsumer;
import com.example.myshop.coupon.infrastructure.coupon.CouponJpaRepository;
import com.example.myshop.coupon.infrastructure.Issuedcoupon.IssuedCouponJpaRepository;
import com.example.myshop.exception.IllegalStatusException;
import com.example.myshop.exception.InvalidParamException;
import com.example.myshop.member.domain.Member;
import com.example.myshop.member.infrastructure.MemberJpaRepository;
import com.example.myshop.order.domain.Order;
import com.example.myshop.order.domain.OrderReader;
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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.example.myshop.ApiFixtures.*;
import static com.example.myshop.CoreFixtures.*;
import static com.example.myshop.constant.Constant.CATEGORY_PATH_DELIMITER;
import static com.example.myshop.order.application.OrderCommand.*;
import static org.assertj.core.api.Assertions.*;


@IntegrationTest
@Transactional
class OrderServiceTest {

    @Autowired
    OrderService sut;

    @Autowired
    OrderReader orderReader;

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

    @Test
    void 주문_등록() {
        //given
        Member member = createMember();
        Item item = createItem(createCategory());
        createInventory(item.getId());

        RegisterOrderCommand command = aRegisterOrderCommandBuilder()
                .memberId(member.getId())
                .orderItems(Collections.singletonList(aRegisterOrderItemCommand()
                    .memberId(member.getId())
                    .itemId(item.getId())
                    .itemPrice(item.getItemPrice())
                    .build()))
                .build();

        //when
        Long orderId = sut.registerOrder(command);

        //then
        Order findOrder = orderJpaRepository.findById(orderId).get();
        assertThat(findOrder.getId()).isEqualTo(orderId);
    }

    @Test
    void 결제수단_오류() {
        //given
        Member member = createMember();
        Item item = createItem(createCategory());
        createInventory(item.getId());

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
        orderJpaRepository.save(order);

        PaymentCommand paymentCommand = aPaymentCommand()
                .payMethod(PayMethod.KAKAO_PAY)
                .build();

        //when &then
        assertThatThrownBy(() -> sut.paymentOrder(order.getId(), paymentCommand))
                .isInstanceOf(InvalidParamException.class);
    }

    @Test
    void 결제금액_오류() {
        //given
        Member member = createMember();
        Item item = createItem(createCategory());
        createInventory(item.getId());

        Order order = anOrder()
                .memberId(member.getId())
                .orderItems(Collections.singletonList(
                    anOrderItem()
                        .itemName(item.getItemName())
                        .memberId(member.getId())
                        .itemPrice(item.getItemPrice())
                        .itemId(item.getId())
                        .build()
                ))
                .build();
        order.orderComplete();
        orderJpaRepository.save(order);

        PaymentCommand paymentCommand = aPaymentCommand()
                .payMethod(PayMethod.KAKAO_PAY)
                .amount(Money.valueOf(10001))
                .build();

        //when &then
        assertThatThrownBy(() -> sut.paymentOrder(order.getId(), paymentCommand))
                .isInstanceOf(InvalidParamException.class);
    }

    @Test
    void 주문상태_오류_롤백() {
        //given
        int initQuantity = 10;
        int orderCount = 1;
        Member member = createMember();
        Item item = createItem(createCategory());
        createInventory(item.getId(), initQuantity);

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

        PaymentCommand paymentCommand = aPaymentCommand()
                .payMethod(PayMethod.KAKAO_PAY)
                .amount(Money.valueOf(10001))
                .build();

        //when &then
        assertThatThrownBy(() -> sut.paymentOrder(order.getId(), paymentCommand))
                .isInstanceOf(InvalidParamException.class);
    }

    @Test
    void 주문_취소_상태_오류() {
        //given
        Member member = createMember();
        Item item = createItem(createCategory());
        createInventory(item.getId());

        Order order = anOrder()
                .memberId(member.getId())
                .orderItems(Collections.singletonList(
                    anOrderItem()
                        .itemName(item.getItemName())
                        .memberId(member.getId())
                        .itemPrice(item.getItemPrice())
                        .itemId(item.getId())
                        .build()
                ))
                .build();

        order.orderComplete();
        order.deliveryPrepare();
        order.inDelivery();
        orderJpaRepository.save(order);

        PaymentCancelCommand paymentCancelCommand = aPaymentCancelCommand()
                .payMethod(PayMethod.CARD)
                .amount(Money.valueOf(5000))
                .build();

        //when &then
        assertThatThrownBy(() -> sut.cancelPaymentOrder(order.getId(), paymentCancelCommand))
                .isInstanceOf(IllegalStatusException.class)
                .hasMessage("배송이 시작 되었습니다.");
    }

    @Test
    void 주문_취소_결제금액_오류() {
        //given
        Member member = createMember();
        Item item = createItem(createCategory());
        createInventory(item.getId());

        Order order = anOrder()
                .memberId(member.getId())
                .orderItems(Collections.singletonList(
                    anOrderItem()
                        .itemName(item.getItemName())
                        .memberId(member.getId())
                        .itemPrice(item.getItemPrice())
                        .itemId(item.getId())
                        .build()
                ))
                .build();

        order.orderComplete();
        order.deliveryPrepare();
        orderJpaRepository.save(order);

        PaymentCancelCommand paymentCancelCommand = aPaymentCancelCommand()
                .payMethod(PayMethod.CARD)
                .amount(Money.valueOf(15000))
                .build();

        //given
        assertThatThrownBy(() -> sut.cancelPaymentOrder(order.getId(), paymentCancelCommand))
                .isInstanceOf(InvalidParamException.class)
                .hasMessageContaining("취소 금액 오류");
    }

    @Test
    void 배송지_변경() {
        //given
        Member member = createMember();
        Item item = createItem(createCategory());
        createInventory(item.getId());

        UpdateReceiverInfoCommand command = anUpdateReceiverInfo().build();
        Order order = anOrder()
                .memberId(member.getId())
                .orderItems(Collections.singletonList(
                    anOrderItem()
                        .itemName(item.getItemName())
                        .memberId(member.getId())
                        .itemPrice(item.getItemPrice())
                        .itemId(item.getId())
                        .build()
                ))
                .build();

        order.orderComplete();
        orderJpaRepository.save(order);

        //when &then
        assertThatCode(() -> sut.updateReceiverInfo(order.getId(), command))
                .doesNotThrowAnyException();
    }

    @Test
    void 배송지_변경_오류() {
        //given
        Member member = createMember();
        Item item = createItem(createCategory());
        createInventory(item.getId());

        UpdateReceiverInfoCommand command = anUpdateReceiverInfo().build();

        Order order = anOrder()
                .memberId(member.getId())
                .orderItems(Collections.singletonList(
                    anOrderItem()
                        .itemName(item.getItemName())
                        .memberId(member.getId())
                        .itemPrice(item.getItemPrice())
                        .itemId(item.getId())
                        .build()
                ))
                .build();

        order.orderComplete();
        order.deliveryPrepare();
        order.inDelivery();
        orderJpaRepository.save(order);

        //when &then
        assertThatThrownBy(() -> sut.updateReceiverInfo(order.getId(), command))
                .isInstanceOf(IllegalStatusException.class)
                .hasMessage("배송이 시작 되었습니다.");
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