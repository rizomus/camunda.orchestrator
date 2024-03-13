package com.example.delegate;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class CancelDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        boolean ORDER_CANCELED = (boolean) execution.getVariable("ORDER_CANCELED");

        if (ORDER_CANCELED) {
            throw new BpmnError("cancelByUserErrorCode");
        };


    }
}
