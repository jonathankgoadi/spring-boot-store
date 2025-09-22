package com.johneycodes.store.services;

import com.johneycodes.store.entities.Order;

import java.util.Optional;

public interface PaymentGateway {
        CheckoutSession createCheckoutSession(Order order);
        Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);

}
