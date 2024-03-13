package com.example.entity.util;

import com.example.dto.OrderStatusDto;
import com.example.entity.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Slf4j
public class Util {

    final static String SET_ORDER_STATUS_URL = "http://localhost:8011/order/set-order-status";
    final static RestTemplate restTemplate = new RestTemplate();

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
