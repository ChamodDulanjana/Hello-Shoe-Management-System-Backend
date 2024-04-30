package com.chamoddulanjana.helloshoemanagementsystem.controller;

import com.chamoddulanjana.helloshoemanagementsystem.dto.InventoryDTO;
import com.chamoddulanjana.helloshoemanagementsystem.service.custom.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/inventory")
@RequiredArgsConstructor
public class Inventory {

    private final InventoryService inventoryService;

    @GetMapping("/health")
    public String healthCheck(){
        return "Inventory Health Check";
    }

    @PostMapping
    public InventoryDTO saveInventory(@RequestBody InventoryDTO inventoryDTO){
        return inventoryService.saveInventory(inventoryDTO);
    }

    @GetMapping("/{code}")
    public InventoryDTO getInventoryById(@PathVariable String code){
        return inventoryService.getInventoryById(code);
    }

    @GetMapping("/getAll")
    public List<InventoryDTO> getAllInventory(){
        return inventoryService.getAllInventory();
    }

    @DeleteMapping("/{code}")
    public void deleteInventoryById(@PathVariable String code){
        inventoryService.deleteInventory(code);
    }

    @PutMapping("/{code}")
    public void updateInventory(@RequestBody InventoryDTO inventoryDTO, @PathVariable String code){
        inventoryService.updateInventory(inventoryDTO, code);
    }
}
