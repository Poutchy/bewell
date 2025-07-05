package com.taass.payement_service.repository;

import com.taass.payement_service.model.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentDetails, Long> {
}
