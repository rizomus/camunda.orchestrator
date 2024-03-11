package com.example.service;

import com.example.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class ProcessService {

    private static final String PROCESS_KEY = "ORCHESTRATOR_PROCESS";
    private final RuntimeService runtimeService;


    public void startProcess(OrderDto orderDto) {

        HashMap<String, Object> vars = new HashMap<>();
        vars.put("order", orderDto);

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_KEY, vars);
    }
}
