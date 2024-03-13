package com.example.dto;


import com.example.entity.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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
