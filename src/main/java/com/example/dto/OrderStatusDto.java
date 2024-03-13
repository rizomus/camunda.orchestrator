package com.example.dto;

import com.example.entity.OrderStatus;

public record OrderStatusDto(
        long id,
        OrderStatus orderStatus                     ) {
}
