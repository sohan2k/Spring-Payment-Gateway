package io.sohan.paymentgateway.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    private OrderRepository orderRepository;
    @Value("${Razorpay.keyId}")
    private String key_id;
    @Value("${Razorpay.Secret-key}")
    private String key_secret;


    public OrderService(OrderRepository orderRepository) throws RazorpayException {
        this.orderRepository = orderRepository;
    }

    public void createOrder(@RequestBody OrderDto orderDto) throws RazorpayException {

        RazorpayClient razorpayClient = new RazorpayClient(key_id, key_secret);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", orderDto.getAmount()*100);
        orderRequest.put("receipt", "order_receiptId_"+Math.random()*(9999-1000)+1000);
        orderRequest.put("currency", "INR");

        Order order=razorpayClient.Orders.create(orderRequest);
        System.out.println(order);


    }
    public void save(Order order){
        Orders orders;

        ObjectMapper objectMapper=new ObjectMapper();
        orders=objectMapper.convertValue(order,Orders.class);
    }

    public String getOrder(String id) throws RazorpayException {
        try{
            RazorpayClient razorpayClient = new RazorpayClient(key_id, key_secret);
            Order order=razorpayClient.Orders.fetch(id);
            return order.toString();
        }catch (RazorpayException e){
            e.printStackTrace();

        }
        return "failed";
    }
}
