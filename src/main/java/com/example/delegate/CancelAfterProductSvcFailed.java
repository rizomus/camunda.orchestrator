package com.example.delegate;

import com.example.entity.OrderStatus;
import com.example.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import static com.example.util.Util.changeOrderStatus;

@Slf4j
@Component
@RequiredArgsConstructor
public class CancelAfterProductSvcFailed implements JavaDelegate {

//    @Autowired

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println("\n -------------------------  PRODUCT service error  -----------------------------");

        long ORDER_ID = (long) execution.getVariable("ORDER_ID");
        changeOrderStatus(ORDER_ID, OrderStatus.CANCELED);

        System.out.println("Order status -> CANCELED \n");
    }

}
