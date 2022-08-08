package io.sohan.paymentgateway.repository;

import io.sohan.paymentgateway.model.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payments, String> {
}
