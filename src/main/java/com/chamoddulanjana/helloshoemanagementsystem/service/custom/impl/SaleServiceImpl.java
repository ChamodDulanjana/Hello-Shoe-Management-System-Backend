package com.chamoddulanjana.helloshoemanagementsystem.service.custom.impl;

import com.chamoddulanjana.helloshoemanagementsystem.dao.*;
import com.chamoddulanjana.helloshoemanagementsystem.dto.RefundDTO;
import com.chamoddulanjana.helloshoemanagementsystem.dto.SaleDTO;
import com.chamoddulanjana.helloshoemanagementsystem.dto.SaleDetailsDTO;
import com.chamoddulanjana.helloshoemanagementsystem.entity.Level;
import com.chamoddulanjana.helloshoemanagementsystem.entity.custom.*;
import com.chamoddulanjana.helloshoemanagementsystem.exception.NotFoundException;
import com.chamoddulanjana.helloshoemanagementsystem.service.custom.SaleService;
import com.chamoddulanjana.helloshoemanagementsystem.util.GenerateIds;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleDao saleDao;
    private final SaleDetailsDao saleDetailsDao;
    private final CustomerDao customerDao;
    private final StockDao stockDao;
    private final InventoryDao inventoryDao;
    private final DecimalFormat df = new DecimalFormat("0.00");
    private final Logger logger = LoggerFactory.getLogger(SaleServiceImpl.class);

    @Override
    public void addSale(SaleDTO dto) {
        logger.info("Sale request received");
        Optional<CustomerEntity> customer = Optional.empty();
        if (dto.getCustomerId() != null) {
            customer = customerDao.findById(dto.getCustomerId());
        }
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        List<SaleDetailsDTO> saleDetailsList = dto.getSaleDetailsList();
        saleDetailsList.forEach(saleDTO -> {
            InventoryEntity item = inventoryDao.findById(saleDTO.getItemId().toLowerCase()).orElseThrow(() -> new NotFoundException("Inventory not found " + saleDTO.getItemId()));
            StockEntity stock = stockDao.findByItemId(saleDTO.getItemId().toLowerCase()).orElseThrow(() -> new NotFoundException("Stock not found " + saleDTO.getItemId()));
            if (saleDTO.getSize().equalsIgnoreCase("40")) {
                item.setQuantityOnHand(item.getQuantityOnHand() - saleDTO.getQuantity());
                stock.setSize40(stock.getSize40() - saleDTO.getQuantity());
            } else if (saleDTO.getSize().equalsIgnoreCase("41")) {
                item.setQuantityOnHand(item.getQuantityOnHand() - saleDTO.getQuantity());
                stock.setSize41(stock.getSize41() - saleDTO.getQuantity());
            } else if (saleDTO.getSize().equalsIgnoreCase("42")) {
                item.setQuantityOnHand(item.getQuantityOnHand() - saleDTO.getQuantity());
                stock.setSize42(stock.getSize42() - saleDTO.getQuantity());
            } else if (saleDTO.getSize().equalsIgnoreCase("43")) {
                item.setQuantityOnHand(item.getQuantityOnHand() - saleDTO.getQuantity());
                stock.setSize43(stock.getSize43() - saleDTO.getQuantity());
            } else if (saleDTO.getSize().equalsIgnoreCase("44")) {
                item.setQuantityOnHand(item.getQuantityOnHand() - saleDTO.getQuantity());
                stock.setSize44(stock.getSize44() - saleDTO.getQuantity());
            } else if (saleDTO.getSize().equalsIgnoreCase("45")) {
                item.setQuantityOnHand(item.getQuantityOnHand() - saleDTO.getQuantity());
                stock.setSize45(stock.getSize45() - saleDTO.getQuantity());
            }
            inventoryDao.save(item);
            stockDao.save(stock);
        });

        SaleEntity sale = SaleEntity.builder().saleId(GenerateIds.getId("SAL").toLowerCase()).date(LocalDate.now()).paymentDescription(dto.getPaymentDescription()).time(LocalTime.now()).customer(customer.orElse(null)).cashierName(userName).build();
        saleDao.save(sale);

        saleDetailsList.forEach(saleDTO -> saleDetailsDao
                .save
                        (
                                SaleDetailsEntity
                                        .builder()
                                        .sale(sale)
                                        .price(saleDTO.getPrice())
                                        .inventory(inventoryDao.findById(saleDTO.getItemId().toLowerCase()).orElseThrow(() -> new NotFoundException("Inventory not found " + saleDTO.getItemId())))
                                        .qty(saleDTO.getQuantity())
                                        .saleDetailsId(GenerateIds.getId("SALD").toLowerCase())
                                        .total(saleDTO.getTotal())
                                        .name(saleDTO.getDescription().toLowerCase())
                                        .size(saleDTO.getSize().toLowerCase())
                                        .build()
                        ));

        customer.ifPresent(cus -> {
            cus.setRecentPurchaseDateAndTime(LocalDateTime.now());
            Double totalPoints = dto
                    .getSaleDetailsList()
                    .stream()
                    .mapToDouble(SaleDetailsDTO::getTotal)
                    .sum() / 100.0;
            totalPoints = Double.valueOf(df.format(totalPoints));
            cus.setTotalPoints(cus.getTotalPoints() + totalPoints);

            if (cus.getTotalPoints() < 50) {
                cus.setLevel(Level.NEW);
            } else if (cus.getTotalPoints() >= 50 && cus.getTotalPoints() < 100) {
                cus.setLevel(Level.BRONZE);
            } else if (cus.getTotalPoints() >= 100 && cus.getTotalPoints() < 200) {
                cus.setLevel(Level.SILVER);
            } else if (cus.getTotalPoints() >= 200) {
                cus.setLevel(Level.GOLD);
            }
            System.out.print(cus);
            customerDao.save(cus);
        });
        logger.info("Sale request completed {}", sale.getSaleId());
    }

    @Override
    public SaleDTO getSale(String id) {
        return null;
    }

    @Override
    public List<SaleDetailsDTO> getSaleItem(String id, String itemId) {
        return List.of();
    }

    @Override
    public void refundSaleItem(RefundDTO dto) {

    }
}
