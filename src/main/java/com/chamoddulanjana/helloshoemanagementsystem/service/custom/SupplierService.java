package com.chamoddulanjana.helloshoemanagementsystem.service.custom;

import com.chamoddulanjana.helloshoemanagementsystem.dto.SupplierDTO;
import com.chamoddulanjana.helloshoemanagementsystem.service.SuperService;

import java.util.List;

public interface SupplierService extends SuperService {
    SupplierDTO saveSupplier(SupplierDTO supplierDTO);
    SupplierDTO getSupplierById(String code);
    List<SupplierDTO> getAllSuppliers();
    void deleteSupplier(String code);
    void updateSupplier(SupplierDTO supplierDTO, String code);

}
