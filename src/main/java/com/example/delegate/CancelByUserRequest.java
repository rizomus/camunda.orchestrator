package com.example.delegate;

import com.example.dto.PaymentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpRequest;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class CancelByUserRequest  implements JavaDelegate {

    @Value("${payment.uri}/cancel-payment?order_id={order_id}")
    String PAYMENT_CANCEL_URL;
    private final  RestTemplate restTemplate = new RestTemplate();
    @Override
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println("\n -------------------------  CANCEL error  ----------------------------- \n");

        long ORDER_ID = (long) execution.getVariable("ORDER_ID");

        HashMap<String, Long> params = new HashMap<>();
        params.put("order_id", ORDER_ID);

        restTemplate.execute(PAYMENT_CANCEL_URL, HttpMethod.POST, null, null, params);

        System.out.println("Payment has returned");
    }
}
