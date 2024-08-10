package com.springboot.productservice.service;

import com.springboot.productservice.event.Producer;
import com.springboot.productservice.model.dto.ProductEventModel;
import com.springboot.productservice.model.dto.ProductRequestDto;
import com.springboot.productservice.model.dto.ProductResponseDto;
import com.springboot.productservice.model.entity.Product;
import com.springboot.productservice.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService{
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final Producer producer;
    @Override
    public ProductResponseDto getById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()){
            return null;
        }
        return mapToDto(product.get());
    }

    @Override
    public void create(ProductRequestDto productRequestDto){
        Product product = Product.builder()
                .ref(UUID.randomUUID().toString())
                .name(productRequestDto.getName())
                .price(productRequestDto.getPrice())
                .stockCount(productRequestDto.getStockCount())
                .inStock(productRequestDto.getInStock())
                .build();

        productRepository.save(product);

        ProductEventModel productEventModel = ProductEventModel.builder()
                .ref(product.getRef())
                .name(product.getName())
                .price(product.getPrice())
                .stockCount(product.getStockCount())
                .inStock(product.getInStock())
                .build();

        try {
            producer.sendMessage(productEventModel);
        }catch (JsonProcessingException ex){
            log.info("Message could not sent to queue");
        }
    }

    @Override
    public List<ProductResponseDto> getProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private ProductResponseDto mapToDto(Product product){
        return ProductResponseDto.builder()
                .ref(product.getRef())
                .name(product.getName())
                .price(product.getPrice())
                .stockCount(product.getStockCount())
                .inStock(product.getInStock())
                .build();
    }
}
