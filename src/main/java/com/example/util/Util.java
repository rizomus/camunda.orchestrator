package com.example.util;

import com.example.dto.OrderStatusDto;
import com.example.entity.OrderStatus;
import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class Util {
    
    private static final  RestTemplate restTemplate = new RestTemplate();
    private static String SET_ORDER_STATUS_URL;

    public static void SetOrderStatusUrl(String setOrderStatusUrl) {
        SET_ORDER_STATUS_URL = setOrderStatusUrl;
    }

    public static void changeOrderStatus(long ORDER_ID, OrderStatus newStatus) {

        HashMap<String, String> orderStatusDto = new HashMap<>();
        orderStatusDto.put("id", String.valueOf(ORDER_ID));
        orderStatusDto.put("newStatus", newStatus.name());

        HttpEntity<HashMap> entity = new HttpEntity<>(orderStatusDto);

        try {
            restTemplate.postForEntity(SET_ORDER_STATUS_URL, entity, String.class);

        } catch (ResourceAccessException e) {
            log.debug("ResourceAccessException");

        } catch (HttpClientErrorException e) {
            log.error("Change Order Status error!");
            log.error(e.getMessage());
            // 400 Wrong Status
            // 400 ID not found
        }
    }
}
