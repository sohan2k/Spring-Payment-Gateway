package io.sohan.paymentgateway;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay")
public class TestGateway {


    @Value("${Razorpay.keyId}")
    private String key_id;
    @Value("${Razorpay.Secret-key}")
    private String key_secret;

    @PostMapping
    public String createOrder(@RequestBody PaymentOrder paymentOrder) throws RazorpayException {

        RazorpayClient razorpayClient = new RazorpayClient(key_id, key_secret);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", paymentOrder.getAmount()*100);
        orderRequest.put("receipt", "order_receiptId_"+Math.random()*(9999-1000)+1000);
        orderRequest.put("currency", "INR");

        Order order=razorpayClient.Orders.create(orderRequest);
        System.out.println(order);
        return "Successfully created";
    }
}
