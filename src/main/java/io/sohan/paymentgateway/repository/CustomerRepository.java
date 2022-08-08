package io.sohan.paymentgateway.repository;

import io.sohan.paymentgateway.model.Customers;
import io.sohan.paymentgateway.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customers, Long> {

    Optional<Customers> findByContact(String contact);


}
