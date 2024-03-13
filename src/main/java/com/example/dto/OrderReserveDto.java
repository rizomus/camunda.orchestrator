package com.example.dto;

import com.example.entity.CurrencyUnit;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
public class OrderReserveDto  {

        long orderId;
        BigDecimal paymentSum;
        CurrencyUnit currencyUnit;

}
