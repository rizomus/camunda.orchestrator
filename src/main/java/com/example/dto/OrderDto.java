package com.example.dto;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import org.camunda.bpm.engine.variable.value.DateValue;
import spinjar.com.fasterxml.jackson.annotation.JsonFormat;

@Data
public class OrderDto {

        long orderId;
        long ownerId;
        String ownerName;
//        @JsonFormat(shape = JsonFormat.Shape.ARRAY)
        List<Product> productList;
        boolean prepayment;
//        @JsonFormat(shape = JsonFormat.Shape.STRING)
        LocalDateTime date;
        OrderStatus status;
}
