package com.example.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class CancelAfterOrderSvcFailed implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        System.out.println("\n ------------------------- ORDER error -----------------------------\n");

    }
}
