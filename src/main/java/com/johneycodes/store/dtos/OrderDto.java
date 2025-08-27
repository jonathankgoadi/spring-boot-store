package com.johneycodes.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Data
@ToString
public class OrderDto {
    private String id;
    private String status;
    private LocalDate createdAt;
    private List<OrderItemDto> items ;
    private BigDecimal totalPrice ;
}
