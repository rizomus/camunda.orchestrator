package com.example.controller;

import com.example.dto.OrderDto;
import com.example.service.ProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.exception.NullValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gateway")
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final ProcessService processService;

    private final RuntimeService runtimeService;            // TODO: move to service


    @PostMapping("/new-order")
    public String startProcess (@RequestBody OrderDto orderDto) {

        String execution_id = processService.startProcess(orderDto);
        System.out.println("\n ======================== \n  PROCESS STARTED \n ======================== \n");
        System.out.println("execution_id: " + execution_id);
        System.out.println();
        return execution_id;
    }

    @PostMapping("/cancel-order")
    public ResponseEntity<Object> cancelOrder(@RequestHeader String executionId) {

        runtimeService.setVariable(executionId, "ORDER_CANCELED", true);
        long order_id = (long) runtimeService.getVariable(executionId, "ORDER_ID");

        log.debug("ORDER " + order_id + " CANCELED BY USER");
        return ResponseEntity.ok("Order canceled");
    }
}
