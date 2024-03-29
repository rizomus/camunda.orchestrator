package com.example.delegate;

import com.example.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.dmn.engine.impl.spi.type.DmnDataTypeTransformer;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderDelegate implements JavaDelegate {

    @Value("${order.uri}/new")
    String NEW_ORDER_URL;
    private final static RestTemplate restTemplate = new RestTemplate();


    @Override
    public void execute(DelegateExecution execution) throws Exception {

//        DmnDataTypeTransformer typeTransformer =

        OrderDto registeredOrder = null;

        System.out.println("\n =========================== \n  ORDER DELEGATE IS RUNNING \n ===========================\n");

        OrderDto orderRequest = (OrderDto) execution.getVariable("order");

        HttpEntity<OrderDto> entity = new HttpEntity<>(orderRequest);
        try {
            registeredOrder = restTemplate.postForEntity(NEW_ORDER_URL, entity, OrderDto.class).getBody();
            log.debug("ORDER_ID: " + registeredOrder.getOrderId());
            execution.setVariable("ORDER_ID", registeredOrder.getOrderId());

        } catch (ResourceAccessException e) {
            log.debug("ResourceAccessException");
            throw new BpmnError("DelegateCancelableErrorCode");
        }
        log.debug("NEW ORDER_ID: " + registeredOrder.getOrderId());

    }
}
