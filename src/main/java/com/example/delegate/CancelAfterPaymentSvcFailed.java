package com.example.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Slf4j
public class CancelAfterPaymentSvcFailed implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("  ====================\n TODO: ROLLBACK AFTER FAILING PAYMENT-SERVICE \n  ====================");
    }
}
