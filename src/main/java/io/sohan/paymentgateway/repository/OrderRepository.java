package io.sohan.paymentgateway.repository;

import io.sohan.paymentgateway.model.Customers;
import io.sohan.paymentgateway.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders,String> {

}
