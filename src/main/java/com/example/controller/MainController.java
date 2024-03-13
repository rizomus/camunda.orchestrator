package com.example.controller;

import com.example.dto.OrderDto;
import com.example.service.ProcessService;
import jakarta.ws.rs.HeaderParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final ProcessService processService;

    private final RuntimeService runtimeService;            // TODO: move to service

    @RequestMapping("/new-order")
    public String startProcess (@RequestBody OrderDto orderDto) {

        String execution_id = processService.startProcess(orderDto);
        System.out.println(" ======================== \n  PROCESS STARTED \n ======================== \n");
        System.out.println("execution_id: " + execution_id);
        System.out.println(orderDto);
        System.out.println();
        return execution_id;
    }

    @PostMapping("/cancel-order")
    public void cancelOrder(@RequestHeader String execution_id) {
        runtimeService.setVariable(execution_id, "ORDER_CANCELED", true);
        log.debug("ORDER CANCELED BY USER");
    }
}
