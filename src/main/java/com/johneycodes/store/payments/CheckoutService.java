package com.johneycodes.store.payments;

import com.johneycodes.store.entities.Order;
import com.johneycodes.store.exceptions.CartEmptyException;
import com.johneycodes.store.exceptions.CartNotFoundException;
import com.johneycodes.store.repositories.CartRepository;
import com.johneycodes.store.repositories.OrderRepository;
import com.johneycodes.store.auth.AuthService;
import com.johneycodes.store.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void  handleWebhookEvent(WebhookRequest request) {
        paymentGateway
                .parseWebhookRequest(request)
                .ifPresent(paymentResult -> {
                    var order = orderRepository.findById(paymentResult.getOrderId()).orElseThrow();
                    order.setStatus(paymentResult.getPaymentStatus());
                    orderRepository.save(order);
                });


    }
}
