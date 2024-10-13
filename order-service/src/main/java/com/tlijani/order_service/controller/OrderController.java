package com.tlijani.order_service.controller;


import com.tlijani.order_service.dto.OrderRequest;
import com.tlijani.order_service.model.Order;
import com.tlijani.order_service.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    @ResponseStatus
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackmethod")
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "Order placed succefully";
    }

    public String fallbackmethod(OrderRequest orderRequest, RuntimeException runtimeException){
        return "Something wrong , Order after some time!!!!!!" ;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
}
