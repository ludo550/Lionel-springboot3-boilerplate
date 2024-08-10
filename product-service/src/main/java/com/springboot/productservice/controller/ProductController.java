package com.springboot.productservice.controller;

import com.springboot.productservice.model.dto.ProductRequestDto;
import com.springboot.productservice.model.dto.ProductResponseDto;
import com.springboot.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private final ProductService productService;

    @PostMapping
    public ResponseEntity createProduct(@RequestBody ProductRequestDto productRequestDto) {
        productService.create(productRequestDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long productId){
        return new ResponseEntity<>(productService.getById(productId), HttpStatus.OK);
    }
}
