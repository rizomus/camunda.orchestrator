package com.example.delegate;


import com.example.dto.OrderDto;
import com.example.dto.OrderReserveDto;
import com.example.dto.Product;
import com.example.dto.ProductReserveDto;
import com.example.entity.CurrencyUnit;
import com.example.entity.OrderStatus;
import com.example.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.util.Util.changeOrderStatus;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductDelegate implements JavaDelegate {

    @Value("${product.uri}/reserve")
    String NEW_ORDER_URL;
    private final static RestTemplate restTemplate = new RestTemplate();

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println("""  
                                          
                \u0020=============================
                \u0020 PRODUCT DELEGATE IS RUNNING
                \u00A0=============================
                                                    """);

        long ORDER_ID = (long) execution.getVariable("ORDER_ID");
        OrderDto order = (OrderDto) execution.getVariable("order");
        Map<Long, Integer> articles = order.getProductList().stream().collect(Collectors.toMap(Product::getArticle, Product::getAmount));
        log.debug("ORDER_ID: " + ORDER_ID);
        ProductReserveDto[] productReserveDto = new ProductReserveDto[articles.size()];

        int i = 0;
        for (var entry: articles.entrySet()) {
            productReserveDto[i] = ProductReserveDto.builder()
                    .orderId(ORDER_ID)
                    .article(entry.getKey())
                    .amount(entry.getValue())
                            .build();
            i++;
        }

        HttpEntity<ProductReserveDto[]> entity = new HttpEntity<>(productReserveDto);

        OrderReserveDto productServiceResponse = new OrderReserveDto();
        try {
            LinkedHashMap body = restTemplate.postForEntity(NEW_ORDER_URL, entity, LinkedHashMap.class).getBody();

            productServiceResponse.setOrderId(((Long.parseLong(body.get("orderId").toString()))));
            productServiceResponse.setPaymentSum((new BigDecimal(body.get("paymentSum").toString())));
            productServiceResponse.setCurrencyUnit(CurrencyUnit.valueOf(body.get("currencyUnit").toString()));

            execution.setVariable("Required-Payment-Info", productServiceResponse);
            OrderReserveDto requiredPaymentInfo = (OrderReserveDto) execution.getVariable("Required-Payment-Info");
            changeOrderStatus(ORDER_ID, OrderStatus.RESERVED);
            log.debug("Order reserved. Required payment " + requiredPaymentInfo.getPaymentSum() + " " + requiredPaymentInfo.getCurrencyUnit().name());

        } catch (HttpClientErrorException e) {
            // 404 NOTFOUND
            // 406 CONFLICT ( optimistic lock ex || multiply currency unit )
            log.debug(e.getMessage());
            throw new BpmnError("productErrorCode");

        } catch (RestClientException e) {
            log.debug("Rest Client Exception");
            log.debug(e.getMessage());
            throw new BpmnError("productErrorCode");
        }

    }
}