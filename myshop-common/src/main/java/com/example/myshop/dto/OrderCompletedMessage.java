package com.example.myshop.dto;

import com.example.myshop.common.Event;
import lombok.*;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderCompletedMessage extends Event {
    private Long orderId;
    private String txId;
}
