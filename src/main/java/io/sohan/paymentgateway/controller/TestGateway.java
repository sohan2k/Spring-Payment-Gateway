package io.sohan.paymentgateway.controller;

import io.sohan.paymentgateway.dto.OrderDto;
import io.sohan.paymentgateway.model.Orders;
import io.sohan.paymentgateway.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestGateway {
    private final OrderService orderService;

    public TestGateway(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public String createOrder(@RequestBody OrderDto orderDto) {
            orderService.createOrder(orderDto);
        return "Successfully created";
    }
    @GetMapping("{id}")
    public Orders getOrder(@PathVariable String id){
        return orderService.getOrder(id);
    }
}
