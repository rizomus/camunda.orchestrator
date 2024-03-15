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
        List<Product> productList;
        boolean prepayment;
        String marketplace;
        LocalDateTime date;
        OrderStatus status;
}
