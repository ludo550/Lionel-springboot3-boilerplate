package com.springboot.productstockservice.service;

import com.springboot.productstockservice.model.dto.CreateProductDto;
import com.springboot.productstockservice.model.dto.ProductStockResponseDto;

import java.util.List;

public interface ProductService {
    //ProductResponseDto getById(Long id);
    void create(CreateProductDto createProductDto);
    ProductStockResponseDto checkProductsStock(List<String> productRefs);
    //List<ProductResponseDto> getProducts();
}
