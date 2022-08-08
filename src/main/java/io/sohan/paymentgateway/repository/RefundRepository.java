package io.sohan.paymentgateway.repository;

import io.sohan.paymentgateway.model.Refunds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundRepository extends JpaRepository<Refunds,String> {
}
