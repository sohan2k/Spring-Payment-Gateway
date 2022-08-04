package io.sohan.paymentgateway.controller;

import com.google.gson.Gson;
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


//    @RequestMapping(value="/createPayment", method= RequestMethod.POST)
//    @ResponseBody
//    public ResponseEntity<String> checkout(@RequestBody OrderDto amt) {
//        Gson gson=new Gson();
//        //return orderService.checkout(orderDto);
//        return new ResponseEntity<>(gson.toJson(orderService.checkout(amt)), HttpStatus.OK);
//    }


    @RequestMapping(value="/pending")
    public String getTest() {
        return "pending";
    }

//    @PostMapping("/page")
//    @ResponseBody
//    public String testPage(@RequestBody OrderDto orderDto) {
//        Gson gson=new Gson();
//        System.out.println(gson.toJson(orderDto));
//        return gson.toJson(orderDto);
//    }
    @PostMapping(value="/createPayment")
    @ResponseBody
    public ResponseEntity<String> checkout(@RequestBody OrderDto amt) {
        Gson gson=new Gson();
        //return orderService.checkout(orderDto);
        return new ResponseEntity<>(gson.toJson(orderService.checkout(amt)), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @ResponseBody
    public String getOrder(@PathVariable String id){
        System.out.println(id);
        Gson gson=new Gson();
        return gson.toJson(orderService.getOrder(id));
    }

}
