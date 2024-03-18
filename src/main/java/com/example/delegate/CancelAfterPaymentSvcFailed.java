package com.example.delegate;

import com.example.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class CancelAfterPaymentSvcFailed implements JavaDelegate {

    @Value("${product.uri}/unreserve?order_id={order_id}")
    String UNRESERVE_PRODUCT_URL;

    private final static RestTemplate restTemplate = new RestTemplate();

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("\n ------------------------- PAYMENT error  -----------------------------\n");

        long ORDER_ID = (long) execution.getVariable("ORDER_ID");

        HashMap<String, Long> params = new HashMap<>();
        params.put("order_id", ORDER_ID);

        restTemplate.execute(UNRESERVE_PRODUCT_URL, HttpMethod.POST, null, null, params);

        System.out.println("Products unreserved");

    }
}
