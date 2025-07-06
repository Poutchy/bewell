package com.taass.payement_service.service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.taass.payement_service.dto.PaymentDTO;
import com.taass.payement_service.dto.PaymentRequest;
import com.taass.payement_service.model.PaymentDetails;
import com.taass.payement_service.model.PaymentStatus;
import com.taass.payement_service.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayPalService {

    private final APIContext apiContext;
    private final PaymentRepository paymentRepository;
    private PaymentDetails paymentSaved;

    String cancelUrl = "http://localhost:8083/payment/cancel";
    String successUrl = "http://localhost:8083/payment/success";

    public Payment createPayment(PaymentRequest paymentRequest) throws PayPalRESTException  {

        Amount amount = new Amount();
        amount.setCurrency(paymentRequest.getCurrency());
        amount.setTotal(String.format(Locale.forLanguageTag(paymentRequest.getCurrency()),"%.2f", paymentRequest.getAmount())); // 9.99$ - 9,99€

        Transaction transaction = new Transaction();
        transaction.setDescription(paymentRequest.getDescription());
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payment payment = getPayment(paymentRequest, transactions);

        PaymentDetails paymentDetails = PaymentDetails.builder()
                .bookingId(paymentRequest.getBookingId())
                .amount(paymentRequest.getAmount())
                .currency(paymentRequest.getCurrency())
                .method(paymentRequest.getMethod())
                .description(paymentRequest.getDescription())
                .clientId(paymentRequest.getClientId())
                .createdAt(paymentRequest.getCreatedAt())
                .build();

        paymentDetails.setStatus(PaymentStatus.PENDING); // Set initial status to PENDING

        paymentSaved = paymentRepository.save(paymentDetails);

        return payment.create(apiContext);
    }

    private Payment getPayment(PaymentRequest paymentRequest, List<Transaction> transactions) {
        Payer payer = new Payer();
        payer.setPaymentMethod(paymentRequest.getMethod()); // "PayPal" or "credit_card"

        Payment payment = new Payment();
        payment.setIntent("sale"); // "sale", "authorize", or "order"
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);

        payment.setRedirectUrls(redirectUrls);
        return payment;
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecution);
    }

    @Transactional
    public PaymentDTO savePaymentDetails() {

        paymentSaved.setStatus(PaymentStatus.COMPLETED); // Set initial status to PENDING

        PaymentDetails finalPayment = paymentRepository.save(paymentSaved);
        return mapToPaymentDTO(finalPayment);
    }

    private PaymentDTO mapToPaymentDTO(PaymentDetails paymentDetails) {
        return PaymentDTO.builder()
                .id(paymentDetails.getId())
                .bookingId(paymentDetails.getBookingId())
                .amount(paymentDetails.getAmount())
                .currency(paymentDetails.getCurrency())
                .method(paymentDetails.getMethod())
                .description(paymentDetails.getDescription())
                .clientId(paymentDetails.getClientId())
                .status(paymentDetails.getStatus())
                .createdAt(paymentDetails.getCreatedAt())
                .build();
    }
}
