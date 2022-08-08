package io.sohan.paymentgateway.service;

import com.google.gson.Gson;
import com.razorpay.*;
import io.sohan.paymentgateway.dto.OrderCheckDto;
import io.sohan.paymentgateway.dto.OrderDto;
import io.sohan.paymentgateway.model.Orders;
import io.sohan.paymentgateway.model.Payments;
import io.sohan.paymentgateway.model.Refunds;
import io.sohan.paymentgateway.repository.OrderRepository;
import io.sohan.paymentgateway.repository.PaymentRepository;
import io.sohan.paymentgateway.repository.RefundRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final RefundRepository refundRepository;
    @Value("${Razorpay.keyId}")
    private String key_id;
    @Value("${Razorpay.Secret-key}")
    private String key_secret;

    private  static final Gson gson=new Gson();

    public OrderService(OrderRepository orderRepository, PaymentRepository paymentRepository, RefundRepository refundRepository) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.refundRepository = refundRepository;
    }

    public void createOrder(@RequestBody OrderDto orderDto){
        try {
            RazorpayClient razorpayClient = new RazorpayClient(key_id, key_secret);
            JSONObject orderRequest = new JSONObject();
            int amt=Integer.parseInt(convertRupeeToPaise(orderDto.getAmount()));
            orderRequest.put("amount", amt);
            orderRequest.put("receipt", "order_receiptId_"+Math.random()*(9999-1000)+1000);
            orderRequest.put("currency", "INR");

            Order order=razorpayClient.Orders.create(orderRequest);
            save(order);
            System.out.println(order);
        }catch (RazorpayException e){
            e.printStackTrace();

        }
    }

    public Orders checkout(@RequestBody OrderDto orderDto){
        try {
            RazorpayClient razorpayClient = new RazorpayClient(key_id, key_secret);
            JSONObject orderRequest = new JSONObject();
            int amnt=Integer.parseInt(orderDto.getAmount());
            orderRequest.put("amount", amnt*100);
            orderRequest.put("receipt", "order_receiptId_"+Math.random()*(9999-1000)+1000);
            orderRequest.put("currency", "INR");

            Order order=razorpayClient.Orders.create(orderRequest);
//            System.out.println(order);
            return save(order);
        }catch (RazorpayException e){
            e.printStackTrace();

        }
        return null;
    }
    public void checkPayment(OrderCheckDto orderCheckDto, String id) throws RazorpayException {
        //orderRepository.save(orders);
        RazorpayClient razorpay = new RazorpayClient(key_id, key_secret);

        String paymentId = orderCheckDto.getRazorpay_payment_id();

        Payment payment = razorpay.Payments.fetch(paymentId);
        Payments payments=savePayment(payment);
        System.out.println(payment);
        if(payment.get("status").equals("captured")){
            Orders orders=getOrder(orderCheckDto.getRazorpay_order_id());
            orders.setRazorpay_payment_id(paymentId);
            orders.setRazorpay_signature(orderCheckDto.getRazorpay_signature());
            orderRepository.save(orders);
            paymentRepository.save(payments);
            System.out.println("success");
        }else {
            System.out.println("failed");
        }
    }
    public Refunds getRefund(String paymentId,OrderDto orderDto) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(key_id, key_secret);
//        String paymentId = "pay_K30a3EG6YBslf0";

        JSONObject refundRequest = new JSONObject();
        refundRequest.put("amount",convertRupeeToPaise(orderDto.getAmount()));
        refundRequest.put("speed","normal");
        JSONObject notes = new JSONObject();
        notes.put("notes_key_1","Test Refund");
        refundRequest.put("notes",notes);
        Refund refund = razorpay.Payments.refund(paymentId,refundRequest);
//        Refunds refunds=gson.fromJson(refund.toString(),Refunds.class);
//        refundRepository.save(refunds);
        Payment payment = razorpay.Payments.fetch(paymentId);
        savePayment(payment);
        System.out.println(refund);
        return saveRefund(refund);
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
//        Gson gson=new Gson();
        Orders orders=gson.fromJson(order.toString(),Orders.class);
        if(!orderRepository.existsById(orders.getId())){
            orders=orderRepository.save(orders);
        }
        else if(orderRepository.existsById(orders.getId()) && orders.getStatus()!="created"){
            Orders orders1=orderRepository.getById(orders.getId());
            orders1.setStatus(orders.getStatus());
            orders1.setAmount_due(orders.getAmount_due());
            orders1.setAmount_paid(orders.getAmount_paid());
            orders1.setAttempts(orders.getAttempts());
            orders=orderRepository.save(orders1);
        }
        System.out.println(orders);
        return orders;
    }

    private Payments savePayment(Payment payment){
        Payments payments=gson.fromJson(payment.toString(),Payments.class);
        payments=paymentRepository.save(payments);
        return payments;
    }

    private String convertRupeeToPaise(String paise) {
        BigDecimal b = new BigDecimal(paise);
        BigDecimal value = b.multiply(new BigDecimal("100"));
        return value.setScale(0, RoundingMode.UP).toString();
    }

    private Refunds saveRefund(Refund refund){
        Refunds refunds=gson.fromJson(refund.toString(),Refunds.class);
        refunds=refundRepository.save(refunds);
        return refunds;
    }
}
