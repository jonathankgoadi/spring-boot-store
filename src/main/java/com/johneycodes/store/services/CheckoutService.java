package com.johneycodes.store.services;

import com.johneycodes.store.dtos.CheckoutRequestDto;
import com.johneycodes.store.dtos.CheckoutResponse;
import com.johneycodes.store.dtos.ErrorDto;
import com.johneycodes.store.entities.Order;
import com.johneycodes.store.exceptions.CartEmptyException;
import com.johneycodes.store.exceptions.CartNotFoundException;
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

    @Value("${websiteUrl}")
    private String websiteUrl;

    @Transactional
    public CheckoutResponse checkout(CheckoutRequestDto request) throws StripeException {
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
           //create a checkout session
           var builder = SessionCreateParams.builder()
                   .setMode(SessionCreateParams.Mode.PAYMENT)
                   .setSuccessUrl(websiteUrl + "/checkout-success?orderId=" + order.getId())
                   .setCancelUrl(websiteUrl + "/checkout-cancel");

           order.getOrderItems().forEach(item -> {
               var lineItem = SessionCreateParams.LineItem.builder()
                       .setQuantity(Long.valueOf(item.getQuantity()))
                       .setPriceData(
                               SessionCreateParams.LineItem.PriceData.builder()
                                       .setCurrency("zar")
                                       .setUnitAmountDecimal(item.getUnitPrice().multiply(BigDecimal.valueOf(100)))
                                       .setProductData(
                                               SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                       .setName(item.getProduct().getName())
                                                       .build()
                                       ).build()
                       ).build();

               builder.addLineItem(lineItem);

           });

           var session = Session.create( builder.build());



           cartService.clearCart(cart.getId());

           return new CheckoutResponse(order.getId(), session.getUrl());
       }catch(StripeException ex){
           orderRepository.delete(order);
           throw ex;
       }
    }
}
