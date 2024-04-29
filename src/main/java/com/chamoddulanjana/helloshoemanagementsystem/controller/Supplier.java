package com.chamoddulanjana.helloshoemanagementsystem.controller;

import com.chamoddulanjana.helloshoemanagementsystem.dto.SupplierDTO;
import com.chamoddulanjana.helloshoemanagementsystem.service.custom.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/supplier")
@RequiredArgsConstructor
public class Supplier {

    private final SupplierService supplierService;

    @GetMapping("/health")
    public String healthCheck(){
        return "Supplier Health Check";
    }

    @PostMapping
    public SupplierDTO saveSupplier(@RequestBody SupplierDTO supplierDTO){
        return supplierService.saveSupplier(supplierDTO);
    }

    @GetMapping("/{code}")
    public SupplierDTO getSupplierById(@PathVariable String code){
        return supplierService.getSupplierById(code);
    }

    @GetMapping("/getAll")
    public List<SupplierDTO> getAllSupplier(){
        return supplierService.getAllSuppliers();
    }

    @DeleteMapping("/{code}")
    public void deleteSupplier(@PathVariable String code){
        supplierService.deleteSupplier(code);
    }

    @PutMapping("/{code}")
    public void updateSupplier(@RequestBody SupplierDTO supplierDTO, @PathVariable String code ){
        supplierService.updateSupplier(supplierDTO, code);
    }
}
