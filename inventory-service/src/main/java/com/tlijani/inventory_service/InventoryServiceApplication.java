package com.tlijani.inventory_service;

import com.tlijani.inventory_service.model.Inventory;
import com.tlijani.inventory_service.repos.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}


	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("iphone_16");
			inventory.setQuantity(100);

			Inventory inventoryRed = new Inventory();
			inventoryRed.setSkuCode("iphone_16_red");
			inventoryRed.setQuantity(0);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventoryRed);
		};
	}



}
