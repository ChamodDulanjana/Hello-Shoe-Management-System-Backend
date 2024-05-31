package com.chamoddulanjana.helloshoemanagementsystem.service.custom.impl;

import com.chamoddulanjana.helloshoemanagementsystem.dao.*;
import com.chamoddulanjana.helloshoemanagementsystem.dto.*;
import com.chamoddulanjana.helloshoemanagementsystem.entity.Level;
import com.chamoddulanjana.helloshoemanagementsystem.entity.custom.*;
import com.chamoddulanjana.helloshoemanagementsystem.exception.NotFoundException;
import com.chamoddulanjana.helloshoemanagementsystem.exception.RefundNotAvailableException;
import com.chamoddulanjana.helloshoemanagementsystem.service.custom.SaleService;
import com.chamoddulanjana.helloshoemanagementsystem.util.Base64Encoder;
import com.chamoddulanjana.helloshoemanagementsystem.util.GenerateIds;
import com.chamoddulanjana.helloshoemanagementsystem.util.InvoiceUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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
    private final InvoiceUtil invoiceUtil;
    private final Base64Encoder base64Encoder;

    @Override
    public ResponseEntity<String> addSale(SaleDTO dto) {
        logger.info("Sale request received");
        AtomicReference<Double> addedPoints = new AtomicReference<>(0.0);
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

        SaleEntity sale = SaleEntity.builder().saleId(GenerateIds.getId("SAL").toLowerCase()).createdAt(LocalDateTime.now()).paymentDescription(dto.getPaymentDescription()).customer(customer.orElse(null)).cashierName(userName).build();
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
            addedPoints.set(dto
                    .getSaleDetailsList()
                    .stream()
                    .mapToDouble(SaleDetailsDTO::getTotal)
                    .sum() / 1000.0);
            addedPoints.set(Double.valueOf(df.format(addedPoints.get())));
            cus.setTotalPoints(cus.getTotalPoints() + addedPoints.get());

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
        InvoiceDTO invoiceDTO = InvoiceDTO.builder().saleId(sale.getSaleId().toUpperCase()).saleDetailsList(saleDetailsList).cashierName(sale.getCashierName().toUpperCase()).customerName(sale.getCustomer() != null ? sale.getCustomer().getCustomerName().toUpperCase() : null).paymentDescription(sale.getPaymentDescription()).addedPoints(addedPoints.get()).totalPoints(sale.getCustomer() != null ? sale.getCustomer().getTotalPoints() : null).rePrinted(false).build();
        byte[] invoice = invoiceUtil.getInvoice(invoiceDTO);
        String s = base64Encoder.encodePdf(invoice);
        logger.info("Sale request completed {}", sale.getSaleId());
        return ResponseEntity.ok().body(s);
    }

    @Override
    public SaleDTO getSale(String id) {
        SaleEntity sale = saleDao.findById(id).orElseThrow(() -> new NotFoundException("Sale not found " + id));
        LocalDateTime date = sale.getCreatedAt();
        long between = ChronoUnit.DAYS.between(date, LocalDateTime.now());
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
                return saleDetailDTOS;
            }
        }
        throw new NotFoundException("Item not found " + itemId);
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

    public OverViewDTO getOverview() {
        logger.info("Get day overview request received");
        List<Object[]> billCount = saleDao.findBillCount(LocalDate.now().toString());
        if (billCount.isEmpty()) throw new NotFoundException("No Sales Found");

        int count = Integer.parseInt(billCount.get(1)[0].toString());
        List<SaleEntity> saleList = saleDao.getAllTodaySales(LocalDate.now().toString()).orElseThrow(() -> new NotFoundException("No Sales Found"));
        Double totalSales = saleList.stream().mapToDouble(sale -> sale.getSaleDetailsList().stream().mapToDouble(SaleDetailsEntity::getTotal).sum()).sum();
        Double totalProfit = saleList.stream().mapToDouble(sale -> sale.getSaleDetailsList().stream().mapToDouble(saleDetails -> saleDetails.getQty() * saleDetails.getInventory().getExpectedProfit()).sum()).sum();
        return OverViewDTO.builder().totalSales(Double.valueOf(df.format(totalSales))).totalProfit(Double.valueOf(df.format(totalProfit))).totalBills(count).build();
    }

    @Override
    public ResponseEntity<String> getAInvoice(String id) {
        logger.info("Get invoice request received {}", id);
        SaleEntity sale = saleDao.findById(id).orElseThrow(() -> new NotFoundException("No Sales Found"));
        return getStringResponseEntity(sale);
    }

    @Override
    public ResponseEntity<String> getLastInvoice() throws IOException {
        logger.info("Last invoice request received");
        Optional<SaleEntity> sale = saleDao.findLatestInvoice();
        if (sale.isEmpty()) throw new NotFoundException("No Sales Found");
        return getStringResponseEntity(sale.get());
    }

    @Override
    public List<SaleDTO> getSales(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return saleDao.findAll(pageable).getContent().stream().map(sale -> SaleDTO.builder().saleId(sale.getSaleId()).paymentDescription(sale.getPaymentDescription()).customerId(sale.getCustomer().getCustomerName()).createdAt(sale.getCreatedAt()).cashierName(sale.getCashierName()).build()).toList();
    }

    @Override
    public List<SaleDTO> searchSales(String search) {
        return saleDao.filterSales(search).stream().map(sale -> SaleDTO.builder().saleId(sale.getSaleId()).paymentDescription(sale.getPaymentDescription()).customerId(sale.getCustomer().getCustomerName()).createdAt(sale.getCreatedAt()).cashierName(sale.getCashierName()).build()).toList();
    }

    private ResponseEntity<String> getStringResponseEntity(SaleEntity sale) {
        List<SaleDetailsEntity> saleDetailsList = sale.getSaleDetailsList();
        List<SaleDetailsDTO> saleDetailDTOS = new ArrayList<>();
        saleDetailsList.forEach(saleDetails -> saleDetailDTOS.add(SaleDetailsDTO.builder().description(saleDetails.getName()).itemId(saleDetails.getInventory().getItemCode()).price(saleDetails.getPrice()).quantity(saleDetails.getQty()).size(saleDetails.getSize()).total(saleDetails.getTotal()).build()));
        InvoiceDTO invoiceDTO = InvoiceDTO.builder().rePrinted(true).saleId(sale.getSaleId().toUpperCase()).saleDetailsList(saleDetailDTOS).cashierName(sale.getCashierName().toUpperCase()).customerName(sale.getCustomer() != null ? sale.getCustomer().getCustomerName().toUpperCase() : null).paymentDescription(sale.getPaymentDescription()).totalPoints(sale.getCustomer()!=null?sale.getCustomer().getTotalPoints():null).addedPoints(sale.getSaleDetailsList().stream().map(SaleDetailsEntity::getTotal).reduce(0.0,Double::sum)/1000.0).build();
        byte[] invoice = invoiceUtil.getInvoice(invoiceDTO);
        String s = base64Encoder.encodePdf(invoice);
        return ResponseEntity.ok().body(s);
    }
}
