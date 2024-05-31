package com.chamoddulanjana.helloshoemanagementsystem.service.custom;

import com.chamoddulanjana.helloshoemanagementsystem.dto.CustomDTO;
import com.chamoddulanjana.helloshoemanagementsystem.dto.InventoryDTO;
import com.chamoddulanjana.helloshoemanagementsystem.service.SuperService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface InventoryService extends SuperService {
    void saveItem(String dto, MultipartFile image) throws IOException;

    void updateItem(String id, String itemDTO, MultipartFile image) throws IOException;

    void deleteItem(String id);

    List<InventoryDTO> getAllByAvailability(Boolean availability, int page, int limit);

    List<InventoryDTO> filterItems(String pattern, Boolean availability);

    InventoryDTO getItem(String id);

    List<CustomDTO> getAllStocks(int page, int limit);

    void updateStock(String id, CustomDTO dto);

    List<CustomDTO> filterStocks(String pattern);

    CustomDTO getStock(String id);

    void activateItem(String id);

    InventoryDTO getPopularItem(Integer filter);
}
