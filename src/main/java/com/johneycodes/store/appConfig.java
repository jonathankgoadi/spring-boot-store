package com.johneycodes.store;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.*")
public class appConfig {
    @Value("${payment.service}")
    private String paymentGateWay;


    @Bean
    public PaymentService stripe(){
        return new StripePaymentService();
    }
    @Bean
    public PaymentService paypal(){
        return new PayPalPaymentService();
    }
    @Bean
    public OrderService orderService(){

        if(paymentGateWay.equalsIgnoreCase("stripe")) {
            return new OrderService(stripe());
        }

        return   new OrderService(paypal());


    }
}
