package com.taass.payement_service.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.taass.payement_service.dto.PaymentDTO;
import com.taass.payement_service.dto.PaymentRequest;
import com.taass.payement_service.service.PayPalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@Slf4j
public class PaypalController {

    private final PayPalService payPalService;

    @PostMapping("/create")
    public RedirectView createPayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            Payment payment = payPalService.createPayment(paymentRequest);

            for (Links links: payment.getLinks()) {
                if ("approval_url".equals(links.getRel())) {
                    log.info("Redirecting to PayPal for approval: {}", links.getHref());
                    return new RedirectView(links.getHref()); // Redirect to cancel URL or success URL
                }
            }

        } catch (PayPalRESTException e) {
            log.error("Error creating PayPal payment: {}", e.getMessage());
        }

        return new RedirectView("/payment/error"); // Fallback to home if payment creation fails
    }

    @GetMapping("/success")
    public ResponseEntity<PaymentDTO> paymentSuccess(
            @RequestParam (name = "paymentId") String paymentId,
            @RequestParam (name = "PayerID") String payerId
    ) {
        log.info("Payment was successful");
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);

            if (payment.getState().equals("approved")) {
                log.info("Payment approved: {}", paymentId);
            }

            PaymentDTO paymentDTO = payPalService.savePaymentDetails();
            log.info("Payment executed successfully: {}", paymentId);

            return ResponseEntity.ok(paymentDTO);
        } catch (PayPalRESTException e) {
            log.error("Error executing PayPal payment: {}", e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/cancel")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void paymentCancel() {
        log.info("Payment was cancelled");
    }

    @GetMapping("/error")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void paymentError() {
        log.error("An error occurred during the payment process");
    }
}
