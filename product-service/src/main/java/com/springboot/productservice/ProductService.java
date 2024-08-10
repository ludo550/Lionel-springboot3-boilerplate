package com.springboot.productservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = {"com.springboot.productservice.repository",
        "com.springboot.productservice.service", "com.springboot.productservice.controller"})
public class ProductService {
    @Autowired
    public static void main(String[] args) {
        SpringApplication.run(ProductService.class, args);
    }
}
