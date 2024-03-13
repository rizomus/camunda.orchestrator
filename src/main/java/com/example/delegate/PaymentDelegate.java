package com.example.delegate;

import com.example.dto.OrderDto;
import com.example.dto.OrderReserveDto;
import com.example.dto.PaymentDto;
import com.example.entity.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpRequest;

import static com.example.entity.util.Util.changeOrderStatus;

@Slf4j
@Component
public class PaymentDelegate implements JavaDelegate {

    final static String NEW_PAYMENT_URL = "http://localhost:8013/payment/pay";

    final static RestTemplate restTemplate = new RestTemplate();

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println("\n  ===========================    \n" +
                             "  PAYMENT DELEGATE IS RUNNING    \n" +
                             "  ===========================    \n");

        OrderReserveDto requiredPaymentInfo = (OrderReserveDto) execution.getVariable("Required-Payment-Info");

        log.debug("Required-Payment-Info: " + requiredPaymentInfo);

        OrderDto order = (OrderDto) execution.getVariable("order");
        long ownerId = order.getOwnerId();
        long ORDER_ID = (long) execution.getVariable("ORDER_ID");

        PaymentDto paymentRequest = PaymentDto.builder()
                .amount(requiredPaymentInfo.paymentSum())
                .currencyUnit(requiredPaymentInfo.currencyUnit().toString())
                .orderId(ORDER_ID)
                .payerId(ownerId)
                .receiver("Top-Shop")
                .build();

        HttpEntity<PaymentDto> entity = new HttpEntity<>(paymentRequest);
        PaymentDto paymentResult = null;
        try {
            paymentResult = restTemplate.postForEntity(NEW_PAYMENT_URL, entity, PaymentDto.class).getBody();
            changeOrderStatus(ORDER_ID, OrderStatus.PAID_UP);
        } catch (HttpClientErrorException e) {
            // 404 NOTFOUND
            // 400 BAD_REQUEST ( insufficient found )
            log.debug(e.getStatusCode().toString());
            log.debug(e.getMessage());
            log.debug(e.getResponseBodyAsString());
            throw new BpmnError("paymentErrorCode");
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
        log.debug("PAYMENT RESULT: " + paymentResult);
    }
}
