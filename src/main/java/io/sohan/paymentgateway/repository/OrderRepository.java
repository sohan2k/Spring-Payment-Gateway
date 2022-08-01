package io.sohan.paymentgateway.repository;

import io.sohan.paymentgateway.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders,String> {
}
