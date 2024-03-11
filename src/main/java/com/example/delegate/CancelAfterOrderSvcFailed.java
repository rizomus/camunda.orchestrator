package com.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class CancelAfterOrderSvcFailed implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println("====================\n TODO: ROLLBACK AFTER FAILING ORDER-SERVICE \n====================");

    }
}
