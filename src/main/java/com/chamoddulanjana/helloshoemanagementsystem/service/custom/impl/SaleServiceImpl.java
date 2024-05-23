package com.chamoddulanjana.helloshoemanagementsystem.service.custom.impl;

import com.chamoddulanjana.helloshoemanagementsystem.dao.*;
import com.chamoddulanjana.helloshoemanagementsystem.dto.RefundDTO;
import com.chamoddulanjana.helloshoemanagementsystem.dto.SaleDTO;
import com.chamoddulanjana.helloshoemanagementsystem.dto.SaleDetailsDTO;
import com.chamoddulanjana.helloshoemanagementsystem.entity.Level;
import com.chamoddulanjana.helloshoemanagementsystem.entity.custom.*;
import com.chamoddulanjana.helloshoemanagementsystem.exception.NotFoundException;
import com.chamoddulanjana.helloshoemanagementsystem.exception.RefundNotAvailableException;
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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
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
        SaleEntity sale = saleDao.findById(id).orElseThrow(() -> new NotFoundException("Sale not found " + id));
        LocalDate date = sale.getDate();
        long between = ChronoUnit.DAYS.between(date, LocalDate.now());
        if (between >= 3) {
            throw new RefundNotAvailableException("Refund Not Available for " + id);
        }
        return SaleDTO.builder().saleId(sale.getSaleId()).paymentDescription(sale.getPaymentDescription()).customerId(sale.getCustomer() != null ? sale.getCustomer().getCustomerCode() : "No Customer").build();

    }

    @Override
    public List<SaleDetailsDTO> getSaleItem(String orderId, String itemId) {
        SaleEntity sale = saleDao.findById(orderId).orElseThrow(() -> new NotFoundException("Sale Not Found " + orderId));
        List<SaleDetailsEntity> saleDetailsList = sale.getSaleDetailsList();
        List<SaleDetailsDTO> saleDetailDTOS = new ArrayList<>();
        for (SaleDetailsEntity saleDetails : saleDetailsList) {
            if (saleDetails.getInventory().getItemCode().equalsIgnoreCase(itemId)) {
                saleDetailDTOS.add(SaleDetailsDTO.builder().description(saleDetails.getName()).itemId(saleDetails.getInventory().getItemCode()).price(saleDetails.getPrice()).quantity(saleDetails.getQty()).size(saleDetails.getSize()).total(saleDetails.getTotal()).build());
            }
        }
        return saleDetailDTOS;
    }

    @Override
    public void refundSaleItem(RefundDTO dto) {
        logger.info("Refund sale item request received");
        InventoryEntity item = inventoryDao.findById(dto.getItemId().toLowerCase()).orElseThrow(() -> new NotFoundException("Inventory not found " + dto.getItemId()));
        StockEntity stock = stockDao.findByItemId(dto.getItemId()).orElseThrow(() -> new NotFoundException("Stock not found " + dto.getItemId()));
        SaleEntity sale = saleDao.findById(dto.getOrderId()).orElseThrow(() -> new NotFoundException("Sale not found " + dto.getOrderId()));
        List<SaleDetailsEntity> saleDetailsList = sale.getSaleDetailsList();

        Iterator<SaleDetailsEntity> iterator = saleDetailsList.iterator();

        while (iterator.hasNext()) {
            SaleDetailsEntity saleDetails = iterator.next();

            if (saleDetails.getInventory().getItemCode().equalsIgnoreCase(dto.getItemId()) && saleDetails.getSize().equalsIgnoreCase(dto.getSize())) {

                saleDetails.setQty(saleDetails.getQty() - dto.getQty());
                saleDetails.setTotal(saleDetails.getPrice() * saleDetails.getQty());
                item.setQuantityOnHand(item.getQuantityOnHand() + dto.getQty());

                switch (dto.getSize()) {
                    case "40":
                        stock.setSize40(stock.getSize40() + dto.getQty());
                        break;
                    case "41":
                        stock.setSize41(stock.getSize41() + dto.getQty());
                        break;
                    case "42":
                        stock.setSize42(stock.getSize42() + dto.getQty());
                        break;
                    case "43":
                        stock.setSize43(stock.getSize43() + dto.getQty());
                        break;
                    case "44":
                        stock.setSize44(stock.getSize44() + dto.getQty());
                        break;
                    case "45":
                        stock.setSize45(stock.getSize45() + dto.getQty());
                        break;
                }
                if (saleDetails.getQty() == 0) {
                    iterator.remove();
                }
                if (saleDetails.getQty() < 0) {
                    throw new RefundNotAvailableException("Invalid quantity entered for Id " + dto.getOrderId());
                }
            }
        }
        stockDao.save(stock);
        saleDao.save(sale);
        inventoryDao.save(item);
    }
}
