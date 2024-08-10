package com.springboot.orderservice.controller;

import com.springboot.orderservice.model.dto.OrderDto;
import com.springboot.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    private final OrderService orderService;

    @PostMapping
    @CircuitBreaker(name = "product-stock", fallbackMethod = "productStockFallbackMethod")
    public ResponseEntity<String> createOrder(@RequestBody OrderDto orderDto) {
        orderService.createOrder(orderDto);
        return new ResponseEntity("Order created succesfully.", HttpStatus.CREATED);
    }

    public ResponseEntity<String> productStockFallbackMethod(OrderDto orderDto, RuntimeException exception){
        return  new ResponseEntity<>("Something went wrong for this operation. Please try later...", HttpStatus.SERVICE_UNAVAILABLE);
    }
}
