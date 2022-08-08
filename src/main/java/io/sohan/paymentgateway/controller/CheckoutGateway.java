package io.sohan.paymentgateway.controller;

import com.google.gson.Gson;
import com.razorpay.RazorpayException;
import io.sohan.paymentgateway.dto.OrderCheckDto;
import io.sohan.paymentgateway.dto.OrderDto;
import io.sohan.paymentgateway.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/test")
public class CheckoutGateway {

    private final OrderService orderService;

    public CheckoutGateway(OrderService orderService) {
        this.orderService = orderService;
    }
    @RequestMapping(value="/checkout")
    public String getcheckout() {
        return "checkout";
    }
    @RequestMapping(value="/checkoutpage")
    public String getCartCheckout() {
        return "cart_checkout";
    }

    @RequestMapping(value="/pending")
    public String getTest() {
        return "pending";
    }

    @PostMapping(value="/createPayment")
    @ResponseBody
    public ResponseEntity<String> checkout(@RequestBody OrderDto amt) {
        Gson gson=new Gson();
        System.out.println(amt);
        return new ResponseEntity<>(gson.toJson(orderService.checkout(amt)), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @ResponseBody
    public String getOrder(@PathVariable String id){
        System.out.println(id);
        Gson gson=new Gson();
        return gson.toJson(orderService.getOrder(id));
    }

    @PostMapping(value="/checkPayment")
    @ResponseBody
    public String checkOder(@RequestBody OrderCheckDto orderCheckDto,@RequestParam String id) throws RazorpayException {
        Gson gson=new Gson();
        orderService.checkPayment(orderCheckDto,id);
        return gson.toJson(orderCheckDto);
    }

}
