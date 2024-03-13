package com.example.delegate;

import com.example.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Slf4j
public class CancelAfterPaymentSvcFailed implements JavaDelegate {

    final static String UNRESERVE_PRODUCT_URL = "http://localhost:8012/product/unreserve?order_id={order_id}";
    final static RestTemplate restTemplate = new RestTemplate();
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println(" ------------------------- PAYMENT error  -----------------------------");
        System.out.println(" ------------------- Try to unreserve products  -----------------------");

        long ORDER_ID = (long) execution.getVariable("ORDER_ID");

        HashMap<String, Long> params = new HashMap<>();
        params.put("order_id", ORDER_ID);

        restTemplate.execute(UNRESERVE_PRODUCT_URL, HttpMethod.POST, null, null, params);


    }
}
