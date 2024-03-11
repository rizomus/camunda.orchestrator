package com.example.dto;

import lombok.Builder;

@Builder
public record ProductReserveDto(long orderId,
                                long article,
                                int amount) {
}
