package com.example.dto;

import com.example.entity.CurrencyUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
public record OrderReserveDto(

        long orderId,
        BigDecimal paymentSum,
        CurrencyUnit currencyUnit
) {
}
