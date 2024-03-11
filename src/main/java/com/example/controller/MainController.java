package com.example.controller;

import com.example.dto.OrderDto;
import com.example.service.ProcessService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    ProcessService processService;

    @RequestMapping("/new-order")
    public void startProcess (@RequestBody OrderDto orderDto) {

        processService.startProcess(orderDto);
        System.out.println("  ======================== \n PROCESS STARTED \n  ======================== \n");
        System.out.println(orderDto);
        System.out.println();
    }
}
