package com.example.myshop;

import com.example.myshop.common.jpa.Money;
import com.example.myshop.order.domain.payment.PayMethod;
import com.example.myshop.review.application.ReviewCommand.UpdateReviewCommand;
import com.example.myshop.review.domain.ReviewGrade;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.myshop.order.application.OrderCommand.*;
import static com.example.myshop.review.application.ReviewCommand.RegisterReviewCommand;

public class ApiFixtures {
    /*
     ** 리뷰
     */
    public static RegisterReviewCommand.RegisterReviewCommandBuilder aReviewCommand() {
        return RegisterReviewCommand.builder()
                .memberId(1L)
                .itemId(1L)
                .orderId(1L)
                .reviewText("옷이 좋아요")
                .reviewGrade(ReviewGrade.FOUR)
                .reviewImages(List.of(createMockMultipartFile("images/image1.jpg", "reviewImages")));
    }

    public static UpdateReviewCommand.UpdateReviewCommandBuilder aUpdateReviewCommand() {
        return UpdateReviewCommand.builder()
                .reviewText("너무 너무 옷이 좋아요")
                .reviewGrade(ReviewGrade.FIVE)
                .reviewImages(List.of(createMockMultipartFile("images/image1.jpg", "reviewImages")));
    }

    /*
     * 주문
     */
    public static RegisterOrderCommand.RegisterOrderCommandBuilder aRegisterOrderCommandBuilder() {
        return RegisterOrderCommand.builder()
                .memberId(1L)
                .payMethod("card")
                .receiverName("주문자")
                .receiverPhone("번호")
                .receiverZipcode("우편번호")
                .receiverAddress1("주소")
                .receiverAddress2("상세주소")
                .etcMessage("빨리 배달")
                .orderItems(Collections.singletonList(aRegisterOrderItemCommand().build()));

    }

    public static RegisterOrderItemCommand.RegisterOrderItemCommandBuilder aRegisterOrderItemCommand() {
        return RegisterOrderItemCommand.builder()
                .orderCount(1)
                .itemId(1L)
                .memberId(1L)
                .itemName("가방")
                .itemPrice(Money.valueOf(10000))
                .orderItemOptionGroups(Arrays.asList(
                        aRegisterOrderItemOptionGroupCommand()
                                .ordering(1)
                                .itemOptionGroupName("사이즈")
                                .orderItemOptions(Collections.singletonList(aRegisterOrderItemOptionCommand().ordering(1).itemOptionName("large").build()))
                                .build()
                        ,
                        aRegisterOrderItemOptionGroupCommand()
                                .ordering(1)
                                .itemOptionGroupName("컬러")
                                .orderItemOptions(Collections.singletonList(aRegisterOrderItemOptionCommand().ordering(2).itemOptionName("blue").build()))
                                .build()))
                .issueCouponIds(List.of());
    }

    public static RegisterOrderItemOptionGroupCommand.RegisterOrderItemOptionGroupCommandBuilder aRegisterOrderItemOptionGroupCommand() {
        return RegisterOrderItemOptionGroupCommand.builder().ordering(1).itemOptionGroupName("사이즈");
    }

    public static RegisterOrderItemOptionCommand.RegisterOrderItemOptionCommandBuilder aRegisterOrderItemOptionCommand() {
        return RegisterOrderItemOptionCommand.builder().ordering(1).itemOptionName("large");
    }

    public static PaymentCommand.PaymentCommandBuilder aPaymentCommand() {
        return PaymentCommand.builder()
                .amount(Money.valueOf(10000))
                .payMethod(PayMethod.CARD);
    }

    public static PaymentCancelCommand.PaymentCancelCommandBuilder aPaymentCancelCommand() {
        return PaymentCancelCommand.builder()
                .amount(Money.valueOf(10000))
                .payMethod(PayMethod.CARD);
    }

    public static UpdateReceiverInfoCommand.UpdateReceiverInfoCommandBuilder anUpdateReceiverInfo(){
       return UpdateReceiverInfoCommand.builder()
                .receiverName("주문자")
                .receiverPhone("번호")
                .receiverZipcode("우편번호")
                .receiverAddress1("주소")
                .receiverAddress2("상세주소")
                .etcMessage("빨리 배달");
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
