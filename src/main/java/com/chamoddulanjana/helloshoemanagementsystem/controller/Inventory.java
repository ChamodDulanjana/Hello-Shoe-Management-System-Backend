package com.chamoddulanjana.helloshoemanagementsystem.controller;

import com.chamoddulanjana.helloshoemanagementsystem.dto.CustomDTO;
import com.chamoddulanjana.helloshoemanagementsystem.dto.InventoryDTO;
import com.chamoddulanjana.helloshoemanagementsystem.service.custom.InventoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/inventory/items")
@RequiredArgsConstructor
public class Inventory {

    private final InventoryService inventoryService;
    private final Logger logger = LoggerFactory.getLogger(Inventory.class);

    @Secured({"ADMIN", "USER"})
    @GetMapping
    public List<InventoryDTO> getAllItems(@RequestParam(name = "availability") Boolean availability, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "limit", defaultValue = "20") int limit) {
        logger.info("Get All Items Request");
        return inventoryService.getAllByAvailability(availability, page, limit);
    }

    @Secured({"ADMIN", "USER"})
    @GetMapping("/popular")
    public InventoryDTO getPopularItem(@RequestParam(name = "range") Integer range) {
        logger.info("Get Popular Item Request");
        return inventoryService.getPopularItem(range);
    }

    @Secured({"ADMIN", "USER"})
    @GetMapping("/stocks")
    public List<CustomDTO> getAllStocks(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "limit", defaultValue = "20") int limit){
        logger.info("Get All Stocks Request");
        return inventoryService.getAllStocks(page,limit);
    }

    @Secured({"ADMIN", "USER"})
    @GetMapping("/stocks/{id}")
    public CustomDTO getStock(@PathVariable String id) {
        logger.info("Get a Stock Request");
        return inventoryService.getStock(id);
    }

    @GetMapping("/{id}")
    @Secured({"ADMIN", "USER"})
    public InventoryDTO getItem(@PathVariable String id) {
        logger.info("Get Item Request: {}", id);
        return inventoryService.getItem(id);
    }

    @Secured({"ADMIN", "USER"})
    @GetMapping("/stocks/filter/{pattern}")
    public List<CustomDTO> filterStocks(@PathVariable String pattern) {
        logger.info("Filter Stocks Request: {}", pattern);
        return inventoryService.filterStocks(pattern);
    }


    @Secured({"ADMIN", "USER"})
    @GetMapping("/filter/{pattern}")
    public List<InventoryDTO> filterItems(@PathVariable String pattern, @RequestParam(name = "availability") Boolean availability) {
        logger.info("Filter Items Request: {}", pattern);
        return inventoryService.filterItems(pattern, availability);
    }

    @Secured({"ADMIN", "USER"})
    @PostMapping
    public void addItem(@RequestPart String dto, @RequestPart(required = false) MultipartFile image) {
        try {
            logger.info("Add Item Request");
            inventoryService.saveItem(dto, image);
        } catch (IOException e) {
            logger.error("Add Item Request Failed: {}", e.getMessage());
        }
    }

    @Secured("ADMIN")
    @PutMapping("/{id}")
    public void updateItem(@PathVariable String id, @RequestPart String dto, @RequestPart(required = false) MultipartFile image) {
        try {
            logger.info("Update Item Request");
            inventoryService.updateItem(id, dto, image);
        } catch (IOException e) {
            logger.error("Update Item Request Failed: {}", e.getMessage());
        }
    }
    @Secured("ADMIN")
    @PutMapping("/activate/{id}")
    public void activateItem(@PathVariable String id) {
        logger.info("Activate Item Request");
        inventoryService.activateItem(id);
    }
    @Secured("ADMIN")
    @PutMapping("/stocks/{id}")
    public void updateStocks(@PathVariable String id, @Validated @RequestBody CustomDTO dto) {
        logger.info("Update Stock Request");
        inventoryService.updateStock(id, dto);
    }

    @Secured("ADMIN")
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable String id) {
        logger.info("Delete Item Request");
        inventoryService.deleteItem(id);
    }
}
