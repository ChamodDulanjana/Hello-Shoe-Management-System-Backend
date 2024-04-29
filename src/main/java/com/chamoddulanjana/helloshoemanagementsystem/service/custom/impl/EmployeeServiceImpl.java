package com.chamoddulanjana.helloshoemanagementsystem.service.custom.impl;

import com.chamoddulanjana.helloshoemanagementsystem.dao.EmployeeDao;
import com.chamoddulanjana.helloshoemanagementsystem.dto.EmployeeDTO;
import com.chamoddulanjana.helloshoemanagementsystem.entity.custom.EmployeeEntity;
import com.chamoddulanjana.helloshoemanagementsystem.service.custom.EmployeeService;
import com.chamoddulanjana.helloshoemanagementsystem.service.util.Mapping;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;
    private final Mapping mapping;

    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        return mapping.toEmployeeDTO(employeeDao.save(mapping.toEmployeeEntity(employeeDTO)));
    }

    @Override
    public EmployeeDTO getEmployeeById(String code) {
        var byId = employeeDao.findById(code);
        if (byId.isPresent()) {
            return mapping.toEmployeeDTO(employeeDao.getReferenceById(code));
        }
        return null;
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return mapping.toEmployeeDTOList(employeeDao.findAll());
    }

    @Override
    public void deleteEmployeeById(String code) {
        employeeDao.deleteById(code);
    }

    @Override
    public void updateEmployee(EmployeeDTO employeeDTO, String code) {
        var byId = employeeDao.findById(code);
        if (byId.isPresent()) {
            byId.get().setEmployeeName(employeeDTO.getEmployeeName());
            byId.get().setEmployeeProfilePic(employeeDTO.getEmployeeProfilePic());
            byId.get().setGender(employeeDTO.getGender());
            byId.get().setStatus(employeeDTO.getStatus());
            byId.get().setDesignation(employeeDTO.getDesignation());
            byId.get().setRole(employeeDTO.getRole());
            byId.get().setDob(employeeDTO.getDob());
            byId.get().setDateOfJoin(employeeDTO.getDateOfJoin());
            byId.get().setBranch(employeeDTO.getBranch());
            byId.get().setAddressLine1(employeeDTO.getAddressLine1());
            byId.get().setAddressLine2(employeeDTO.getAddressLine2());
            byId.get().setAddressLine3(employeeDTO.getAddressLine3());
            byId.get().setAddressLine4(employeeDTO.getAddressLine4());
            byId.get().setAddressLine5(employeeDTO.getAddressLine5());
            byId.get().setContactNumber(employeeDTO.getContactNumber());
            byId.get().setEmail(employeeDTO.getEmail());
            byId.get().setInformInCaseOfEmergency(employeeDTO.getInformInCaseOfEmergency());
            byId.get().setEmergencyContactNumber(employeeDTO.getEmergencyContactNumber());
        }
    }
}