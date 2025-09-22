package com.johneycodes.store.dtos;

import lombok.Data;

@Data
public class CheckoutResponse {
    private Long orderId;
    private final String checkoutUrl;

    public CheckoutResponse(Long orderId, String checkoutUrl) {
        this.orderId = orderId;
        this.checkoutUrl = checkoutUrl;
    }
}
