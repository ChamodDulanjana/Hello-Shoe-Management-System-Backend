package com.chamoddulanjana.helloshoemanagementsystem.service.custom.impl;

import com.chamoddulanjana.helloshoemanagementsystem.dao.InventoryDao;
import com.chamoddulanjana.helloshoemanagementsystem.dao.SaleDetailsDao;
import com.chamoddulanjana.helloshoemanagementsystem.dao.StockDao;
import com.chamoddulanjana.helloshoemanagementsystem.dao.SupplierDao;
import com.chamoddulanjana.helloshoemanagementsystem.dto.CustomDTO;
import com.chamoddulanjana.helloshoemanagementsystem.dto.InventoryDTO;
import com.chamoddulanjana.helloshoemanagementsystem.entity.custom.InventoryEntity;
import com.chamoddulanjana.helloshoemanagementsystem.entity.custom.StockEntity;
import com.chamoddulanjana.helloshoemanagementsystem.entity.custom.SupplierEntity;
import com.chamoddulanjana.helloshoemanagementsystem.exception.NotFoundException;
import com.chamoddulanjana.helloshoemanagementsystem.service.custom.InventoryService;
import com.chamoddulanjana.helloshoemanagementsystem.util.Base64Encoder;
import com.chamoddulanjana.helloshoemanagementsystem.util.GenerateIds;
import com.chamoddulanjana.helloshoemanagementsystem.util.Mapping;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryDao inventoryDao;
    private final StockDao stockDao;
    private final ObjectMapper objectMapper;
    private final Base64Encoder base64Encoder;
    private final SupplierDao supplierDao;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(InventoryServiceImpl.class);
    private final DecimalFormat df = new DecimalFormat("0.00");
    private final SaleDetailsDao saleDetailsDao;


    @Override
    public List<InventoryDTO> getAllByAvailability(Boolean availability, int page, int limit) {
        logger.info("Get All Items Request");
        Pageable pageable = PageRequest.of(page, limit);
        List<InventoryDTO> itemDTOS = new ArrayList<>();
        Page<InventoryEntity> byAvailability = inventoryDao.findByAvailability(availability, pageable);
        for (InventoryEntity item : byAvailability) {
            getItemDTOs(itemDTOS, item);
        }
        return itemDTOS;
    }

    private void getItemDTOs(List<InventoryDTO> itemDTOS, InventoryEntity inventory) {
        logger.info("Item Found: {}", inventory.getItemCode());
        InventoryDTO dto = InventoryDTO
                .builder()
                .itemCode(inventory.getItemCode())
                .itemDescription(inventory.getItemDescription())
                .itemPicture(inventory.getItemPicture())
                .expectedProfit(inventory.getExpectedProfit())
                .profitMargin(inventory.getProfitMargin())
                .quantityOnHand(inventory.getQuantityOnHand())
                .supplierName(inventory.getSupplierName())
                .supplierCode(inventory.getSupplier().getSupplierCode())
                .unitPrice_buy(inventory.getUnitPrice_buy())
                .unitPrice_sale(inventory.getUnitPrice_sale())
                .category(inventory.getCategory())
                .build();
        itemDTOS.add(dto);
    }

    @Override
    public void saveItem(String itemDTO, MultipartFile image) throws IOException {
        InventoryDTO dto = objectMapper.readValue(itemDTO, InventoryDTO.class);
        String id = (dto.getOccasion() + dto.getVerities() + dto.getGender() + GenerateIds.getId("")).toLowerCase();
        String stockId = GenerateIds.getId("STK").toLowerCase();

        Double expectedProfit = dto.getUnitPrice_sale() - dto.getUnitPrice_buy();
        Double profitMargin = (expectedProfit / dto.getUnitPrice_buy()) * 100;
        profitMargin = Double.parseDouble(df.format(profitMargin));

        SupplierEntity supplier = supplierDao.findById(dto.getSupplierCode().toLowerCase()).orElseThrow(() -> new NotFoundException("Supplier Not Found"));

        StockEntity stock = StockEntity
                .builder()
                .stockId(stockId)
                .size40(0)
                .size41(0)
                .size42(0)
                .size43(0)
                .size44(0)
                .size45(0)
                .build();

        InventoryEntity item = InventoryEntity
                .builder()
                .itemDescription(dto.getItemDescription().toLowerCase())
                .itemCode(id)
                .category((dto.getOccasion() + "/" + dto.getVerities() + "/" + dto.getGender()).toLowerCase())
                .itemPicture(image != null ? base64Encoder.convertBase64(image) : null)
                .unitPrice_buy(dto.getUnitPrice_buy())
                .unitPrice_sale(dto.getUnitPrice_sale())
                .quantityOnHand(0)
                .supplierName(supplier.getSupplierName())
                .expectedProfit(expectedProfit)
                .profitMargin(profitMargin)
                .supplier(supplier)
                .stock(stock)
                .availability(true)
                .build();

        stock.setInventory(item);
        inventoryDao.save(item);
        logger.info("Item Added: {}", item.getItemCode());
    }

    @Override
    public void updateItem(String id, String itemDTO, MultipartFile image) throws IOException {
        InventoryDTO dto = objectMapper.readValue(itemDTO, InventoryDTO.class);
        InventoryEntity item = inventoryDao.findById(id).orElseThrow(() -> new NotFoundException("Item Not Found"));
        SupplierEntity supplier = supplierDao.findById(dto.getSupplierCode().toLowerCase()).orElseThrow(() -> new NotFoundException("Supplier Not Found"));

        Double expectedProfit = dto.getUnitPrice_sale() - dto.getUnitPrice_buy();
        Double profitMargin = (expectedProfit / dto.getUnitPrice_buy()) * 100;
        profitMargin = Double.parseDouble(df.format(profitMargin));

        item.setItemDescription(dto.getItemDescription().toLowerCase());
        item.setCategory((dto.getOccasion() + "/" + dto.getVerities() + "/" + dto.getGender()).toLowerCase());
        item.setUnitPrice_buy(dto.getUnitPrice_buy());
        item.setUnitPrice_sale(dto.getUnitPrice_sale());
        item.setSupplierName(supplier.getSupplierName());
        item.setExpectedProfit(expectedProfit);
        item.setProfitMargin(profitMargin);
        item.setSupplier(supplier);
        item.setItemPicture(image != null ? base64Encoder.convertBase64(image) : item.getItemPicture());
        inventoryDao.save(item);
        logger.info("Item Updated: {}", item.getItemCode());
    }

    @Override
    public void deleteItem(String id) {
        InventoryEntity item = inventoryDao.findById(id).orElseThrow(() -> new NotFoundException("Item Not Found"));
        item.setAvailability(false);
        inventoryDao.save(item);
        logger.info("Item Deleted: {}", id);
    }

    @Override
    public List<InventoryDTO> filterItems(String pattern, Boolean availability) {
        List<InventoryDTO> itemDTOS = new ArrayList<>();
        for (InventoryEntity item : inventoryDao.filterItems(pattern)) {
            if (item.getAvailability()) getItemDTOs(itemDTOS, item);
        }
        logger.info("Filtered Items");
        return itemDTOS;
    }

    @Override
    public InventoryDTO getItem(String id) {
        logger.info("Get Item Request: {}", id);
        InventoryEntity item = inventoryDao.findByAvailabilityAndItemId(true, id).orElseThrow(() -> new NotFoundException("Item Not Found"));
        return InventoryDTO
                .builder()
                .itemCode(item.getItemCode())
                .itemDescription(item.getItemDescription())
                .itemPicture(item.getItemPicture())
                .expectedProfit(item.getExpectedProfit())
                .profitMargin(item.getProfitMargin())
                .quantityOnHand(item.getQuantityOnHand())
                .supplierName(item.getSupplierName())
                .supplierCode(item.getSupplier().getSupplierCode())
                .unitPrice_buy(item.getUnitPrice_buy())
                .unitPrice_sale(item.getUnitPrice_sale())
                .category(item.getCategory())
                .build();
    }

    @Override
    public List<CustomDTO> getAllStocks(int page, int limit) {
        logger.info("Get All Stocks Request");
        Pageable pageable = PageRequest.of(page, limit);
        Page<StockEntity> all = stockDao.findAll(pageable);
        List<StockEntity> stocks = all.getContent();
        return getCustomDTOS(stocks);
    }

    @Override
    public void updateStock(String id, CustomDTO dto) {
        StockEntity stock = stockDao.findById(id).orElseThrow(() -> new NotFoundException("Stock Not Found"));
        InventoryEntity item = inventoryDao.findById(stock.getInventory().getItemCode()).orElseThrow(() -> new NotFoundException("Item Not Found"));
        int totalStock = dto.getSize40() + dto.getSize41() + dto.getSize42() + dto.getSize43() + dto.getSize44() + dto.getSize45();
        item.setQuantityOnHand(totalStock);
        stock.setSize40(dto.getSize40());
        stock.setSize41(dto.getSize41());
        stock.setSize42(dto.getSize42());
        stock.setSize43(dto.getSize43());
        stock.setSize44(dto.getSize44());
        stock.setSize45(dto.getSize45());
        stock.setInventory(item);

        stockDao.save(stock);
        logger.info("Stock Updated: {}", stock.getStockId());
    }

    @Override
    public List<CustomDTO> filterStocks(String pattern) {
        logger.info("Filtered Stocks");
        List<Object[]> objects = stockDao.filterStocks(pattern);
        List<CustomDTO> customDTOS = new ArrayList<>();
        for (Object[] object : objects) {
            customDTOS.add(CustomDTO
                    .builder()
                    .itemId(object[0].toString())
                    .stockId(object[1].toString())
                    .description(object[2].toString())
                    .supplierId(object[3].toString())
                    .supplierName(object[4].toString())
                    .size40(Integer.parseInt(object[5].toString()))
                    .size41(Integer.parseInt(object[6].toString()))
                    .size42(Integer.parseInt(object[7].toString()))
                    .size43(Integer.parseInt(object[8].toString()))
                    .size44(Integer.parseInt(object[9].toString()))
                    .size45(Integer.parseInt(object[10].toString()))
                    .build());
        }
        return customDTOS;
    }

    private List<CustomDTO> getCustomDTOS(List<StockEntity> stocks) {
        List<CustomDTO> customDTOS = new ArrayList<>();
        for (StockEntity stock : stocks) {
            customDTOS.add(CustomDTO
                    .builder()
                    .itemId(stock.getInventory().getItemCode())
                    .stockId(stock.getStockId())
                    .description(stock.getInventory().getItemDescription())
                    .supplierId(stock.getInventory().getSupplier().getSupplierCode())
                    .supplierName(stock.getInventory().getSupplierName())
                    .size40(stock.getSize40())
                    .size41(stock.getSize41())
                    .size42(stock.getSize42())
                    .size43(stock.getSize43())
                    .size44(stock.getSize44())
                    .size45(stock.getSize45())
                    .build());
        }
        return customDTOS;
    }

    @Override
    public CustomDTO getStock(String id) {
        StockEntity stock = stockDao.findByItemId(id).orElseThrow(() -> new NotFoundException("Stock Not Found"));
        return CustomDTO
                .builder()
                .size40(stock.getSize40())
                .size41(stock.getSize41())
                .size42(stock.getSize42())
                .size43(stock.getSize43())
                .size44(stock.getSize44())
                .size45(stock.getSize45())
                .itemId(stock.getInventory().getItemCode())
                .build();
    }

    @Override
    public void activateItem(String id) {
        InventoryEntity item = inventoryDao.findById(id).orElseThrow(() -> new NotFoundException("Item Not Found"));
        item.setAvailability(true);
        inventoryDao.save(item);
        logger.info("Item Activated: {}", id);
    }

    @Override
    public InventoryDTO getPopularItem(Integer range) {
        List<Object[]> popularItem = saleDetailsDao.findPopularItem(range).orElseThrow(() -> new NotFoundException("Popular Item Not Found"));
        if (popularItem.isEmpty()) throw new NotFoundException("Popular Item Not Found");
        String itemId = popularItem.get(1)[0].toString();
        InventoryEntity item = inventoryDao.findById(itemId).orElseThrow(() -> new NotFoundException("Item Not Found"));
        logger.info("Popular Item Found: {}", itemId);
        return InventoryDTO
                .builder()
                .itemCode(item.getItemCode())
                .itemDescription(item.getItemDescription())
                .itemPicture(item.getItemPicture())
                .expectedProfit(item.getExpectedProfit())
                .profitMargin(item.getProfitMargin())
                .quantityOnHand(item.getQuantityOnHand())
                .supplierName(item.getSupplierName())
                .supplierCode(item.getSupplier().getSupplierCode())
                .unitPrice_buy(item.getUnitPrice_buy())
                .unitPrice_sale(item.getUnitPrice_sale())
                .category(item.getCategory())
                .build();
    }
}
