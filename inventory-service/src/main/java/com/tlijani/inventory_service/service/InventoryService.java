package com.tlijani.inventory_service.service;


import com.tlijani.inventory_service.repos.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;

@Service
@RequiredArgsConstructor
public class InventoryService {


    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode){
        return  inventoryRepository.findBySkuCode(skuCode).isPresent();

    }

}
