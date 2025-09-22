package com.johneycodes.store.controllers;

import com.johneycodes.store.dtos.CheckoutRequestDto;
import com.johneycodes.store.dtos.CheckoutResponse;
import com.johneycodes.store.dtos.ErrorDto;
import com.johneycodes.store.entities.OrderStatus;
import com.johneycodes.store.exceptions.CartEmptyException;
import com.johneycodes.store.exceptions.CartNotFoundException;
import com.johneycodes.store.exceptions.PaymentException;
import com.johneycodes.store.repositories.OrderRepository;
import com.johneycodes.store.services.CheckoutService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;
    private final OrderRepository orderRepository;

    @Value("${stripe.webhookSecretKey}")
    private  String webhookSecretKey;

    @PostMapping
    public CheckoutResponse checkout(@RequestBody CheckoutRequestDto request) {
        return checkoutService.checkout(request);
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(
            @RequestHeader("Stripe-Signature") String signature,
           @RequestBody  String payload
    ){
        try {
            var  event = Webhook.constructEvent(payload,signature, webhookSecretKey);
            System.out.println("event type: " + event.getType());

           var stripeObject = event.getDataObjectDeserializer().getObject().orElse(null);

           switch (event.getType()) {
               case "payment_intent.succeeded" -> {
                   //Update  order status(paid)
                   var paymentIntent = (PaymentIntent)  stripeObject;
                   if (paymentIntent != null) {
                   var orderId = paymentIntent.getMetadata().get("order_id");
                   var order = orderRepository.findById(Long.valueOf(orderId)).orElseThrow();
                   order.setStatus(OrderStatus.PAID);
                   System.out.println("order status: " + order.getStatus());
                   orderRepository.save(order);
                   }
               }
               case "payment_intent.failed" -> {
                   //update order status (failed)
               }

           }
            return ResponseEntity.ok().build();

        } catch (SignatureVerificationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorDto> handlePaymentException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("Error creating a checkout session"));
    }
    @ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleException(Exception ex) {
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }
}
