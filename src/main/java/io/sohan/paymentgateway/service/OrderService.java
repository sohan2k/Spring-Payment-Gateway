package io.sohan.paymentgateway.service;

import com.google.gson.Gson;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import io.sohan.paymentgateway.dto.OrderDto;
import io.sohan.paymentgateway.model.Orders;
import io.sohan.paymentgateway.repository.OrderRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    @Value("${Razorpay.keyId}")
    private String key_id;
    @Value("${Razorpay.Secret-key}")
    private String key_secret;


    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createOrder(@RequestBody OrderDto orderDto){
        try {
            RazorpayClient razorpayClient = new RazorpayClient(key_id, key_secret);
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", orderDto.getAmount()*100);
            orderRequest.put("receipt", "order_receiptId_"+Math.random()*(9999-1000)+1000);
            orderRequest.put("currency", "INR");

            Order order=razorpayClient.Orders.create(orderRequest);
            save(order);
            System.out.println(order);
        }catch (RazorpayException e){
            e.printStackTrace();

        }
    }


    public Orders getOrder(String id){
        try{
            RazorpayClient razorpayClient = new RazorpayClient(key_id, key_secret);
            Order order=razorpayClient.Orders.fetch(id);
            return save(order);
        }catch (RazorpayException e){
            e.printStackTrace();

        }
        return null;
    }


    public Orders save(Order order){
        Gson gson=new Gson();
        Orders orders=gson.fromJson(order.toString(),Orders.class);
        if(!orderRepository.existsById(orders.getId())){
            orderRepository.save(orders);
        }
        System.out.println(orders);
        return orders;
    }
}
