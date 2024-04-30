package com.chamoddulanjana.helloshoemanagementsystem.service.custom.impl;

import com.chamoddulanjana.helloshoemanagementsystem.dao.InventoryDao;
import com.chamoddulanjana.helloshoemanagementsystem.dto.InventoryDTO;
import com.chamoddulanjana.helloshoemanagementsystem.entity.custom.InventoryEntity;
import com.chamoddulanjana.helloshoemanagementsystem.service.custom.InventoryService;
import com.chamoddulanjana.helloshoemanagementsystem.service.util.Mapping;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryDao inventoryDao;
    private final Mapping mapping;

    @Override
    public InventoryDTO saveInventory(InventoryDTO inventoryDTO) {
        return mapping.toInventoryDTO(inventoryDao.save(mapping.toInventoryEntity(inventoryDTO)));
    }

    @Override
    public InventoryDTO getInventoryById(String code) {
        var byId = inventoryDao.findById(code);
        if (byId.isPresent()) {
            return mapping.toInventoryDTO(inventoryDao.getReferenceById(code));
        }
        return null;
    }

    @Override
    public List<InventoryDTO> getAllInventory() {
        return mapping.toInventoryDTOList(inventoryDao.findAll());
    }

    @Override
    public void deleteInventory(String code) {
        inventoryDao.deleteById(code);
    }

    @Override
    public void updateInventory(InventoryDTO inventoryDTO, String code) {
        var byId = inventoryDao.findById(code);
        if (byId.isPresent()) {
            byId.get().setItemDescription(inventoryDTO.getItemDescription());
            byId.get().setItemPicture(inventoryDTO.getItemPicture());
            byId.get().setCategory(inventoryDTO.getCategory());
            byId.get().setSize(inventoryDTO.getSize());
            byId.get().setSupplierCode(inventoryDTO.getSupplierCode());
            byId.get().setSupplierName(inventoryDTO.getSupplierName());
            byId.get().setQuantityOnHand(inventoryDTO.getQuantityOnHand());
            byId.get().setUnitPrice_sale(inventoryDTO.getUnitPrice_sale());
            byId.get().setUnitPrice_buy(inventoryDTO.getUnitPrice_buy());
            byId.get().setExpectedProfit(inventoryDTO.getExpectedProfit());
            byId.get().setProfitMargin(inventoryDTO.getProfitMargin());
            byId.get().setStatus(inventoryDTO.getStatus());
        }
    }
}
