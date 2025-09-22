package com.johneycodes.store.payments;


import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
    @Value("${stripe.secretKey}")
    private String  stripeKey;

    @PostConstruct
    public void inti(){
        Stripe.apiKey = stripeKey;
    }
}
