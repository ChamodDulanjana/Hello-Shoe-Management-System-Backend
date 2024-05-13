package com.chamoddulanjana.helloshoemanagementsystem.service.custom.impl;

import com.chamoddulanjana.helloshoemanagementsystem.dao.SupplierDao;
import com.chamoddulanjana.helloshoemanagementsystem.dto.SupplierDTO;
import com.chamoddulanjana.helloshoemanagementsystem.service.custom.SupplierService;
import com.chamoddulanjana.helloshoemanagementsystem.util.Mapping;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierDao supplierDao;
    private final Mapping mapping;

    @Override
    public SupplierDTO saveSupplier(SupplierDTO supplierDTO) {
        return mapping.toSupplierDTO(supplierDao.save(mapping.toSupplierEntity(supplierDTO)));
    }

    @Override
    public SupplierDTO getSupplierById(String code) {
        var byId = supplierDao.findById(code);
        if (byId.isPresent()) {
            return mapping.toSupplierDTO(supplierDao.getReferenceById(code));
        }
        return null;
    }

    @Override
    public List<SupplierDTO> getAllSuppliers() {
        return mapping.toSupplierDTOList(supplierDao.findAll());
    }

    @Override
    public void deleteSupplier(String code) {
        supplierDao.deleteById(code);
    }

    @Override
    public void updateSupplier(SupplierDTO supplierDTO, String code) {
        var byId = supplierDao.findById(code);
        if (byId.isPresent()) {
            byId.get().setSupplierName(supplierDTO.getSupplierName());
            byId.get().setCategory(supplierDTO.getCategory());
            byId.get().setAddressLine1(supplierDTO.getAddressLine1());
            byId.get().setAddressLine2(supplierDTO.getAddressLine2());
            byId.get().setAddressLine3(supplierDTO.getAddressLine3());
            byId.get().setAddressLine4(supplierDTO.getAddressLine4());
            byId.get().setAddressLine5(supplierDTO.getAddressLine5());
            byId.get().setAddressLine6(supplierDTO.getAddressLine6());
            byId.get().setContactNumber1(supplierDTO.getContactNumber1());
            byId.get().setContactNumber2(supplierDTO.getContactNumber2());
            byId.get().setEmail(supplierDTO.getEmail());
        }
    }
}
