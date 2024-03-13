package com.example.delegate;

import com.example.dto.PaymentDto;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpRequest;
import java.util.HashMap;

public class CancelByUserRequest  implements JavaDelegate {

    final static String PAYMENT_CANCEL_URL = "http://localhost:8013/payment/cancel-payment?order_id={order_id}";
    final static RestTemplate restTemplate = new RestTemplate();

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println(" -------------------------  CANCEL error  -----------------------------");
        System.out.println(" --------------------  Try to return payment  -------------------------");

        long ORDER_ID = (long) execution.getVariable("ORDER_ID");

        HashMap<String, Long> params = new HashMap<>();
        params.put("order_id", ORDER_ID);

        restTemplate.execute(PAYMENT_CANCEL_URL, HttpMethod.POST, null, null, params);

    }
}
