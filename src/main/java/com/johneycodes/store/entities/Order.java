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
    private PaymentStatus status;


    @Column(name = "created_at",insertable = false, updatable = false)
    private LocalDate createdAt;


    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<OrderItem> orderItems = new LinkedHashSet<>();

   public static Order fromCart(Cart cart, User customer) {
       var order = new Order();
       order.setCustomer(customer);
       order.setStatus(PaymentStatus.PENDING);
       order.setTotalPrice(cart.getTotalPrice());

       cart.getCartItems().forEach(item -> {
           var orderItem = new OrderItem(order, item.getProduct(),item.getQuantity());

           order.orderItems.add(orderItem);
       });

       return order;
   }

   public boolean isPlacedBy(User customer){
       return this.customer.equals(customer);
   }
}