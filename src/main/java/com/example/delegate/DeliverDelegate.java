package com.example.delegate;

import com.example.entity.OrderStatus;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.web.client.RestTemplate;

import static com.example.entity.util.Util.changeOrderStatus;

public class DeliverDelegate  implements JavaDelegate {


    @Override
    public void execute(DelegateExecution execution) throws Exception {

        long ORDER_ID = (long) execution.getVariable("ORDER_ID");
        changeOrderStatus(ORDER_ID, OrderStatus.SENT);
    }
}
