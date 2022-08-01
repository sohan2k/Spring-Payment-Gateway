package io.sohan.paymentgateway.controller;

import com.razorpay.RazorpayException;
import io.sohan.paymentgateway.dto.OrderDto;
import io.sohan.paymentgateway.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestGateway {
    private OrderService orderService;

    @Value("${Razorpay.keyId}")
    private String key_id;
    @Value("${Razorpay.Secret-key}")
    private String key_secret;

    public TestGateway(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public String createOrder(@RequestBody OrderDto orderDto) throws RazorpayException {

//        RazorpayClient razorpayClient = new RazorpayClient(key_id, key_secret);
//        JSONObject orderRequest = new JSONObject();
//        orderRequest.put("amount", orderDto.getAmount()*100);
//        orderRequest.put("receipt", "order_receiptId_"+Math.random()*(9999-1000)+1000);
//        orderRequest.put("currency", "INR");
//
//        Order order=razorpayClient.Orders.create(orderRequest);
//        System.out.println(order);
        return "Successfully created";
    }
    @GetMapping("{id}")
    public String getOrder(@PathVariable String id) throws RazorpayException {
        return orderService.getOrder(id);
    }
}
