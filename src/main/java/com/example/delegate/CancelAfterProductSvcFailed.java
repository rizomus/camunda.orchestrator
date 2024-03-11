package com.example.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class CancelAfterProductSvcFailed implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("=============================================\n" +
                "TODO: ROLLBACK AFTER FAILING PRODUCT-SERVICE " +
                "\n=============================================");
    }
}
