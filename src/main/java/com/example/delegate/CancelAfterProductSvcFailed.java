package com.example.delegate;

import com.example.entity.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


import static com.example.entity.util.Util.changeOrderStatus;

@Slf4j
public class CancelAfterProductSvcFailed implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println("------------------------------------------\n" +
                           " ROLLBACK AFTER FAILING PRODUCT-SERVICE "     +
                         "\n------------------------------------------");

        long ORDER_ID = (long) execution.getVariable("ORDER_ID");
        changeOrderStatus(ORDER_ID, OrderStatus.CANCELED);
    }

}
