package com.example.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentDto(

    BigDecimal amount,
    long orderId,
    String currencyUnit,
    long payerId,
    String receiver,
    boolean successful
)
{}
