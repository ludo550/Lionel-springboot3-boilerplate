package com.springboot.orderservice.service;

import com.springboot.orderservice.model.dto.OrderDto;

public interface OrderService{
    void createOrder(OrderDto orderDto);
}
