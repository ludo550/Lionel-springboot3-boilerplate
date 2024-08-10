package com.springboot.orderservice.service;

import com.springboot.orderservice.event.Producer;
import com.springboot.orderservice.model.dto.OrderDto;
import com.springboot.orderservice.model.dto.OrderItemDto;
import com.springboot.orderservice.model.dto.ProductStockResponseDto;
import com.springboot.orderservice.model.entity.Order;
import com.springboot.orderservice.model.entity.OrderItem;
import com.springboot.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final Producer producer;
    private final WebClient.Builder loadBalancedWebClientBuilder;
    @Autowired
    private final OrderRepository orderRepository;
    @Override
    public void createOrder(OrderDto orderDto) {
        List<String> orderItemRefs = orderDto.getOrderItemDtoList().stream()
                .map(OrderItemDto::getRef)
                .collect(Collectors.toList());

        ProductStockResponseDto productStockResponseDto  = loadBalancedWebClientBuilder.build().get()
                .uri("http://product-stock-service/api/product-stocks",
                        uriBuilder -> uriBuilder.queryParam("productRefs", orderItemRefs).build())
                .retrieve()
                .bodyToMono(ProductStockResponseDto.class)
                .block();

        if(productStockResponseDto.isError() && productStockResponseDto.getProductStockResponses().size() <= 0){
            throw new IllegalArgumentException(productStockResponseDto.getDescription());
        }
        boolean allProductsInStock = productStockResponseDto.getProductStockResponses().stream()
                        .allMatch(productStockResponse -> productStockResponse.isInStock() ==  true);

        if(allProductsInStock){
            orderRepository.save(mapToEntity(orderDto));
            String userPhoneNumber = String.valueOf(new Random().nextInt(900000000) + 1000000000);
            producer.sendEvent(userPhoneNumber);
        }
    }

    private Order mapToEntity(OrderDto orderDto){
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDto orderItemDto : orderDto.getOrderItemDtoList()){
            OrderItem orderItem = OrderItem.builder()
                    .ref(orderItemDto.getRef())
                    .price(orderItemDto.getPrice())
                    .quantity(orderItemDto.getQuantity())
                    .build();
            orderItems.add(orderItem);
        }

        return Order.builder()
                .orderItems(orderItems)
                .build();
    }
}
