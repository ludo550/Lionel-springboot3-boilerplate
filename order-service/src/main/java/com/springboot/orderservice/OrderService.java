package com.springboot.orderservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = {"com.springboot.orderservice.repository",
        "com.springboot.orderservice.service", "com.springboot.orderservice.controller"})
public class OrderService {
    @Autowired
    public static void main(String[] args) {
        SpringApplication.run(OrderService.class, args);
    }
}
