package com.johneycodes.store.controllers;

import com.johneycodes.store.dtos.CheckoutRequestDto;
import com.johneycodes.store.dtos.CheckoutResponse;
import com.johneycodes.store.dtos.ErrorDto;
import com.johneycodes.store.exceptions.CartEmptyException;
import com.johneycodes.store.exceptions.CartNotFoundException;
import com.johneycodes.store.exceptions.PaymentException;
import com.johneycodes.store.services.CheckoutService;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping("/checkout")
    public CheckoutResponse checkout(@RequestBody CheckoutRequestDto request) {
        return checkoutService.checkout(request);
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
