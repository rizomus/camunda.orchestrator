package com.example.delegate;

import com.example.entity.OrderStatus;
import com.example.util.Util;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.util.Util.changeOrderStatus;


@Component
@RequiredArgsConstructor
public class DeliverDelegate  implements JavaDelegate {


    @Override
    public void execute(DelegateExecution execution) throws Exception {

        long ORDER_ID = (long) execution.getVariable("ORDER_ID");
        changeOrderStatus(ORDER_ID, OrderStatus.SENT);
    }
}
