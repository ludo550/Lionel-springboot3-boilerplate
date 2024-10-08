package com.springboot.productservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductEvent {
    private String ref;
    private String name;
    private BigDecimal price;
    private Integer stockCount;
    private Boolean inStock;
}
