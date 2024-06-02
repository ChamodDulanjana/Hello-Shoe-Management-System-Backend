package com.chamoddulanjana.helloshoesapplicationsystem.service.impl;

import com.chamoddulanjana.helloshoesapplicationsystem.entity.*;
import com.chamoddulanjana.helloshoesapplicationsystem.exception.customExceptions.NotFoundException;
import com.chamoddulanjana.helloshoesapplicationsystem.exception.customExceptions.RefundNotAvailableException;
import com.chamoddulanjana.helloshoesapplicationsystem.repository.*;
import com.chamoddulanjana.helloshoesapplicationsystem.service.SaleService;
import com.chamoddulanjana.helloshoesapplicationsystem.dto.OverViewDTO;
import com.chamoddulanjana.helloshoesapplicationsystem.dto.RefundDTO;
import com.chamoddulanjana.helloshoesapplicationsystem.dto.SaleDTO;
import com.chamoddulanjana.helloshoesapplicationsystem.dto.SaleDetailDTO;
import com.chamoddulanjana.helloshoesapplicationsystem.enums.Level;
import com.chamoddulanjana.helloshoesapplicationsystem.util.Base64Encoder;
import com.chamoddulanjana.helloshoesapplicationsystem.util.GenerateId;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private final SaleRepository saleRepository;
    private final SaleDetailsRepository saleDetailsRepository;
    private final CustomerRepository customerRepository;
    private final StocksRepository stocksRepository;
    private final InventoryRepository inventoryRepository;
    private final Base64Encoder base64Encoder;
    private final DecimalFormat df = new DecimalFormat("0.00");
    private final Logger LOGGER = LoggerFactory.getLogger(SaleServiceImpl.class);


    @Override
    public void addSale(SaleDTO dto) {
        LOGGER.info("Sale request received");
        AtomicReference<Double> addedPoints = new AtomicReference<>(0.0);
        Optional<Customer> customer = Optional.empty();
        if (dto.getCustomerId() != null) {
            customer = customerRepository.findById(dto.getCustomerId());
        }
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        List<SaleDetailDTO> saleDetailsList = dto.getSaleDetailsList();
        saleDetailsList.forEach(saleDTO -> {
            Item item = inventoryRepository.findById(saleDTO.getItemId().toLowerCase()).orElseThrow(() -> new NotFoundException("Inventory not found " + saleDTO.getItemId()));
            Stock stock = stocksRepository.findByItemId(saleDTO.getItemId().toLowerCase()).orElseThrow(() -> new NotFoundException("Stock not found " + saleDTO.getItemId()));
            if (saleDTO.getSize().equalsIgnoreCase("40")) {
                item.setQuantity(item.getQuantity() - saleDTO.getQuantity());
                stock.setSize40(stock.getSize40() - saleDTO.getQuantity());
            } else if (saleDTO.getSize().equalsIgnoreCase("41")) {
                item.setQuantity(item.getQuantity() - saleDTO.getQuantity());
                stock.setSize41(stock.getSize41() - saleDTO.getQuantity());
            } else if (saleDTO.getSize().equalsIgnoreCase("42")) {
                item.setQuantity(item.getQuantity() - saleDTO.getQuantity());
                stock.setSize42(stock.getSize42() - saleDTO.getQuantity());
            } else if (saleDTO.getSize().equalsIgnoreCase("43")) {
                item.setQuantity(item.getQuantity() - saleDTO.getQuantity());
                stock.setSize43(stock.getSize43() - saleDTO.getQuantity());
            } else if (saleDTO.getSize().equalsIgnoreCase("44")) {
                item.setQuantity(item.getQuantity() - saleDTO.getQuantity());
                stock.setSize44(stock.getSize44() - saleDTO.getQuantity());
            } else if (saleDTO.getSize().equalsIgnoreCase("45")) {
                item.setQuantity(item.getQuantity() - saleDTO.getQuantity());
                stock.setSize45(stock.getSize45() - saleDTO.getQuantity());
            }
            inventoryRepository.save(item);
            stocksRepository.save(stock);
        });

        Sale sale = Sale.builder().saleId(GenerateId.getId("SAL").toLowerCase()).createdAt(LocalDateTime.now()).paymentDescription(dto.getPaymentDescription()).customer(customer.orElse(null)).cashierName(userName).build();
        saleRepository.save(sale);

        saleDetailsList.forEach(saleDTO -> saleDetailsRepository
                .save
                        (
                                SaleDetails
                                        .builder()
                                        .sale(sale)
                                        .price(saleDTO.getPrice())
                                        .item(inventoryRepository.findById(saleDTO.getItemId().toLowerCase()).orElseThrow(() -> new NotFoundException("Inventory not found " + saleDTO.getItemId())))
                                        .qty(saleDTO.getQuantity())
                                        .saleDetailsId(GenerateId.getId("SALD").toLowerCase())
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
                    .mapToDouble(SaleDetailDTO::getTotal)
                    .sum() / 1000.0);
            addedPoints.set(Double.valueOf(df.format(addedPoints.get())));
            cus.setTotalPoints(cus.getTotalPoints() + addedPoints.get());

            if (cus.getTotalPoints() < 50) {
                cus.setLevel(Level.New);
            } else if (cus.getTotalPoints() >= 50 && cus.getTotalPoints() < 100) {
                cus.setLevel(Level.Bronze);
            } else if (cus.getTotalPoints() >= 100 && cus.getTotalPoints() < 200) {
                cus.setLevel(Level.Silver);
            } else if (cus.getTotalPoints() >= 200) {
                cus.setLevel(Level.Gold);
            }
            System.out.print(cus);
            customerRepository.save(cus);
        });

    }

    @Override
    public SaleDTO getSale(String id) {
        Sale sale = saleRepository.findById(id).orElseThrow(() -> new NotFoundException("Sale not found " + id));
        LocalDateTime date = sale.getCreatedAt();
        long between = ChronoUnit.DAYS.between(date, LocalDateTime.now());
        if (between >= 3) {
            throw new RefundNotAvailableException("Refund Not Available for " + id);
        }
        return SaleDTO.builder().saleId(sale.getSaleId()).paymentDescription(sale.getPaymentDescription()).customerId(sale.getCustomer() != null ? sale.getCustomer().getCustomerId() : "No Customer").build();
    }

    @Override
    public List<SaleDetailDTO> getSaleItem(String orderId, String itemId) {
        Sale sale = saleRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Sale Not Found " + orderId));
        List<SaleDetails> saleDetailsList = sale.getSaleDetailsList();
        List<SaleDetailDTO> saleDetailDTOS = new ArrayList<>();
        for (SaleDetails saleDetails : saleDetailsList) {
            if (saleDetails.getItem().getItemId().equalsIgnoreCase(itemId)) {
                saleDetailDTOS.add(SaleDetailDTO.builder().description(saleDetails.getName()).itemId(saleDetails.getItem().getItemId()).price(saleDetails.getPrice()).quantity(saleDetails.getQty()).size(saleDetails.getSize()).total(saleDetails.getTotal()).build());
                return saleDetailDTOS;
            }
        }
        throw new NotFoundException("Item not found " + itemId);
    }

    @Override
    public void refundSaleItem(RefundDTO dto) {
        LOGGER.info("Refund sale item request received");
        Item item = inventoryRepository.findById(dto.getItemId().toLowerCase()).orElseThrow(() -> new NotFoundException("Inventory not found " + dto.getItemId()));
        Stock stock = stocksRepository.findByItemId(dto.getItemId()).orElseThrow(() -> new NotFoundException("Stock not found " + dto.getItemId()));
        Sale sale = saleRepository.findById(dto.getOrderId()).orElseThrow(() -> new NotFoundException("Sale not found " + dto.getOrderId()));
        List<SaleDetails> saleDetailsList = sale.getSaleDetailsList();

        Iterator<SaleDetails> iterator = saleDetailsList.iterator();

        while (iterator.hasNext()) {
            SaleDetails saleDetails = iterator.next();

            if (saleDetails.getItem().getItemId().equalsIgnoreCase(dto.getItemId()) && saleDetails.getSize().equalsIgnoreCase(dto.getSize())) {

                saleDetails.setQty(saleDetails.getQty() - dto.getQty());
                saleDetails.setTotal(saleDetails.getPrice() * saleDetails.getQty());
                item.setQuantity(item.getQuantity() + dto.getQty());

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
        stocksRepository.save(stock);
        saleRepository.save(sale);
        inventoryRepository.save(item);
    }

    @Override
    public OverViewDTO getOverview() {
        LOGGER.info("Get day overview request received");
        List<Object[]> billCount = saleRepository.findBillCount(LocalDate.now().toString());
        if (billCount.isEmpty()) throw new NotFoundException("No Sales Found");

        int count = Integer.parseInt(billCount.getFirst()[0].toString());
        List<Sale> saleList = saleRepository.getAllTodaySales(LocalDate.now().toString()).orElseThrow(() -> new NotFoundException("No Sales Found"));
        Double totalSales = saleList.stream().mapToDouble(sale -> sale.getSaleDetailsList().stream().mapToDouble(SaleDetails::getTotal).sum()).sum();
        Double totalProfit = saleList.stream().mapToDouble(sale -> sale.getSaleDetailsList().stream().mapToDouble(saleDetails -> saleDetails.getQty() * saleDetails.getItem().getExpectedProfit()).sum()).sum();
        return OverViewDTO.builder().totalSales(Double.valueOf(df.format(totalSales))).totalProfit(Double.valueOf(df.format(totalProfit))).totalBills(count).build();
    }

    @Override
    public List<SaleDTO> getSales(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return saleRepository.findAll(pageable).getContent().stream().map(sale -> SaleDTO.builder().saleId(sale.getSaleId()).paymentDescription(sale.getPaymentDescription()).customerId(sale.getCustomer().getName()).createdAt(sale.getCreatedAt()).cashierName(sale.getCashierName()).build()).toList();
    }

    @Override
    public List<SaleDTO> searchSales(String search) {
        return saleRepository.filterSales(search).stream().map(sale -> SaleDTO.builder().saleId(sale.getSaleId()).paymentDescription(sale.getPaymentDescription()).customerId(sale.getCustomer().getName()).createdAt(sale.getCreatedAt()).cashierName(sale.getCashierName()).build()).toList();
    }

}
