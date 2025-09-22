package com.johneycodes.store.services;

import com.johneycodes.store.entities.Order;

public interface PaymentGateway {
        CheckoutSession createCheckoutSession(Order order);
}
