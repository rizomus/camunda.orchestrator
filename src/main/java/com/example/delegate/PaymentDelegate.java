package com.example.delegate;

import com.example.dto.OrderDto;
import com.example.dto.OrderReserveDto;
import com.example.dto.PaymentDto;
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

import static com.example.util.Util.changeOrderStatus;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentDelegate implements JavaDelegate {

    @Value("${payment.uri}/pay")
    String NEW_PAYMENT_URL;
    private final  RestTemplate restTemplate = new RestTemplate();
    @Override
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println("\n =============================    \n" +
                             "  PAYMENT DELEGATE IS RUNNING     \n" +
                             " =============================    \n");

        OrderReserveDto requiredPaymentInfo = (OrderReserveDto) execution.getVariable("Required-Payment-Info");

        log.debug("Required Payment: " + requiredPaymentInfo.getPaymentSum() + " " + requiredPaymentInfo.getCurrencyUnit());

        OrderDto order = (OrderDto) execution.getVariable("order");
        long ownerId = order.getOwnerId();
        long ORDER_ID = (long) execution.getVariable("ORDER_ID");
        String marketplace = order.getMarketplace();

        PaymentDto paymentRequest = PaymentDto.builder()
                .amount(requiredPaymentInfo.getPaymentSum())
                .currencyUnit(requiredPaymentInfo.getCurrencyUnit().toString())
                .orderId(ORDER_ID)
                .payerId(ownerId)
                .receiver(marketplace)
                .build();

        HttpEntity<PaymentDto> entity = new HttpEntity<>(paymentRequest);
        PaymentDto paymentResult = null;
        try {
            paymentResult = restTemplate.postForEntity(NEW_PAYMENT_URL, entity, PaymentDto.class).getBody();
            changeOrderStatus(ORDER_ID, OrderStatus.PAID_UP);
        } catch (HttpClientErrorException e) {
            // 404 NOTFOUND
            // 400 BAD_REQUEST ( insufficient found )
            if (e.getMessage().startsWith("404")) {
                log.debug("Invalid payer ID");
            } else if (e.getMessage().startsWith("400")) {
                log.debug("Insufficient found");
            } else {
                log.debug(e.getMessage());
            }
            throw new BpmnError("paymentErrorCode");
        } catch (RestClientException e) {
            log.debug("ResourceAccessException!!! \n" + e.getMessage());
        }
        log.debug("PAYMENT RESULT: successful = " + paymentResult.successful());
    }
}
