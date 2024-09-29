package com.tlijani.order_service.service;


import com.tlijani.order_service.dto.InventoryResponse;
import com.tlijani.order_service.dto.OrderLineItemDto;
import com.tlijani.order_service.dto.OrderRequest;
import com.tlijani.order_service.model.Order;
import com.tlijani.order_service.model.OrderLineItem;
import com.tlijani.order_service.repos.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {


    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItems=orderRequest.getOrderLineItemDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemList()
                .stream()
                .map(OrderLineItem::getSkuCode)
                .toList();



        //to be like this http://localhost:8085/api/inventory?skuCode=iphone-16&skuCode=iphone16-red

        InventoryResponse[] inventoryResponses = webClient.get()
                .uri("http://localhost:8087/api/inventory", uriBuilder ->
                        uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();


        boolean allProductsInStock= Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            System.out.println("All products are in stock, saving order"+ order);
            orderRepository.save(order);
        } else {
            System.out.println("Not all products are in stock for order"+ order);

            throw new IllegalArgumentException("Product is not in stock");
        }
    }

    private OrderLineItem mapToDto(OrderLineItemDto orderLineItemDto) {

        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(orderLineItem.getPrice());
        orderLineItem.setQuantity(orderLineItem.getQuantity());
        orderLineItem.setSkuCode(orderLineItem.getSkuCode());

        return orderLineItem;
    }
}
