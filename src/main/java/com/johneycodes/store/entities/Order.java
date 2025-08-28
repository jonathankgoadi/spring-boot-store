package com.johneycodes.store.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;


    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    @Column(name = "created_at",insertable = false, updatable = false)
    private LocalDate createdAt;


    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private Set<OrderItem> orderItems = new LinkedHashSet<>();

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", totalPrice=" + totalPrice +
                ", orderItems=" + orderItems +
                '}';
    }
}