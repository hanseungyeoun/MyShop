package com.example.myshop.dto;

import com.example.myshop.common.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderCanceledMessage extends Event {
    private Long id;
    private String txId;
}
