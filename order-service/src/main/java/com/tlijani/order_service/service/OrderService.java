package com.tlijani.order_service.service;


import com.tlijani.order_service.dto.InventoryResponse;
import com.tlijani.order_service.dto.OrderLineItemDto;
import com.tlijani.order_service.dto.OrderRequest;
import com.tlijani.order_service.event.OrderPlacedEvent;
import com.tlijani.order_service.model.Order;
import com.tlijani.order_service.model.OrderLineItem;
import com.tlijani.order_service.repos.OrderRepository;
import javassist.bytecode.stackmap.Tracer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {


    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);


    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;



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

        logger.info("Placing order: {}", orderRequest);


        //to be like this http://localhost:8085/api/inventory?skuCode=iphone-16&skuCode=iphone16-red

        InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory", uriBuilder ->
                        uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();


        boolean allProductsInStock= Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            System.out.println("All products are in stock, saving order"+ order);
            orderRepository.save(order);

            kafkaTemplate.send("order-placed" , new OrderPlacedEvent(order.getOrderNumber()));



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

    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }

}
