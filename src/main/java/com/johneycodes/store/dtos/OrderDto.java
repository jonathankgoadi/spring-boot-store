package com.johneycodes.store.dtos;

import com.johneycodes.store.entities.Product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderDto {
    private String id;
    private String status;
    private Instant createdAt;
    private List<CartItemDto> items = new ArrayList<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;
}
