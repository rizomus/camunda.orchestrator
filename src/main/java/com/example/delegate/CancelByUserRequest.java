package com.example.delegate;

import com.example.dto.PaymentDto;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpRequest;

public class CancelByUserRequest  implements JavaDelegate {

    final static String PAYMENT_CANCEL_URL = "http://localhost:8013/payment/cancel-payment?order_id=";
    final static RestTemplate restTemplate = new RestTemplate();

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        Object order_id = execution.getVariable("ORDER_ID");
        ResponseEntity<Object> response = restTemplate.postForEntity(PAYMENT_CANCEL_URL + order_id, null, Object.class);

    }
}
