package com.springboot.productservice.service;

import com.springboot.productservice.model.dto.ProductRequestDto;
import com.springboot.productservice.model.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto getById(Long id);
    void create(ProductRequestDto productRequestDto);
    List<ProductResponseDto> getProducts();
}
