package io.sohan.paymentgateway.controller;

import com.google.gson.Gson;
import com.razorpay.RazorpayException;
import io.sohan.paymentgateway.dto.OrderDto;
import io.sohan.paymentgateway.model.Orders;
import io.sohan.paymentgateway.model.Refunds;
import io.sohan.paymentgateway.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
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
        System.out.println(id);
        return orderService.getOrder(id);
    }

    /**
     * This is for just test html
     * @param orderDto
     * @return
     */
    @PostMapping("/page")
    @ResponseBody
    public String testPage(@RequestBody OrderDto orderDto) {
        Gson gson=new Gson();
        System.out.println(orderDto);
        System.out.println(gson.toJson(orderDto));
        return gson.toJson(orderDto);
    }
    @PostMapping("/{id}" +
            "/refund")
    public Refunds testRefund(@PathVariable String id, @RequestBody OrderDto orderDto) throws RazorpayException {
        System.out.println(id);
        System.out.println(orderDto);
        return orderService.getRefund(id,orderDto);
    }


//    @PostMapping(value="/createPayment")
//    @ResponseBody
//    public ResponseEntity<String> checkout(@RequestBody OrderDto amt) {
//        Gson gson=new Gson();
//        //return orderService.checkout(orderDto);
//        return new ResponseEntity<>(gson.toJson(orderService.checkout(amt)), HttpStatus.OK);
//    }
}
