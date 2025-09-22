package com.johneycodes.store.services;

import com.johneycodes.store.dtos.CheckoutRequestDto;
import com.johneycodes.store.dtos.CheckoutResponse;
import com.johneycodes.store.dtos.ErrorDto;
import com.johneycodes.store.entities.Order;
import com.johneycodes.store.exceptions.CartEmptyException;
import com.johneycodes.store.exceptions.CartNotFoundException;
import com.johneycodes.store.exceptions.PaymentException;
import com.johneycodes.store.repositories.CartRepository;
import com.johneycodes.store.repositories.OrderRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;
    private final PaymentGateway paymentGateway;


    @Transactional
    public CheckoutResponse checkout(CheckoutRequestDto request)  {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if(cart == null) {
            throw new CartNotFoundException();
        }

        if(cart.isEmpty()){
            throw new CartEmptyException();
        }

        var order = Order.fromCart(cart, authService.getCurrentUser());

        orderRepository.save(order);

       try {
           var session =  paymentGateway.createCheckoutSession(order);
            cartService.clearCart(cart.getId());

           return new CheckoutResponse(order.getId(), session.getCheckoutUrl());

       }catch(PaymentException ex){
           orderRepository.delete(order);
           throw ex;
       }
    }
}
