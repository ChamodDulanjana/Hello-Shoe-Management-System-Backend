package com.chamoddulanjana.helloshoemanagementsystem.service.custom;

import com.chamoddulanjana.helloshoemanagementsystem.dto.InventoryDTO;
import com.chamoddulanjana.helloshoemanagementsystem.service.SuperService;

import java.util.List;

public interface InventoryService extends SuperService {
    InventoryDTO saveInventory(InventoryDTO inventoryDTO);
    InventoryDTO getInventoryById(String code);
    List<InventoryDTO> getAllInventory();
    void deleteInventory(String code);
    void updateInventory(InventoryDTO inventoryDTO, String code);
}
