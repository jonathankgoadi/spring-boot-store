package com.johneycodes.store.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


    @Column(name = "product_id")
    private Long productId;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;


    @Column(name = "quantity")
    private Integer quantity;


    @Column(name = "total_price")
    private BigDecimal totalPrice;

}