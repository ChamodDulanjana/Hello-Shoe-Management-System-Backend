package com.chamoddulanjana.helloshoemanagementsystem.service.custom.impl;

import com.chamoddulanjana.helloshoemanagementsystem.dao.EmployeeDao;
import com.chamoddulanjana.helloshoemanagementsystem.dto.EmployeeDTO;
import com.chamoddulanjana.helloshoemanagementsystem.entity.custom.EmployeeEntity;
import com.chamoddulanjana.helloshoemanagementsystem.exception.NotFoundException;
import com.chamoddulanjana.helloshoemanagementsystem.service.custom.EmployeeService;
import com.chamoddulanjana.helloshoemanagementsystem.util.GenerateIds;
import com.chamoddulanjana.helloshoemanagementsystem.util.Base64Encoder;
import com.chamoddulanjana.helloshoemanagementsystem.util.Mapping;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final EmployeeDao employeeDao;
    private final Mapping mapping;
    private final Base64Encoder base64Encoder;
    private final ObjectMapper json;

    @Override
    public EmployeeDTO saveEmployee(String employeeDTO, MultipartFile image) throws IOException {
        EmployeeDTO dto = json.readValue(employeeDTO, EmployeeDTO.class);
        employeeDao.findEmployeeByEmail(dto.getEmail().toLowerCase()).ifPresent(employee -> {
            logger.error("Employee Email Exists: {}", dto.getEmail());
            throw new NotFoundException("Employee Email Exists");
        });
        employeeDao.findEmployeeByContactNumber(dto.getContactNumber()).ifPresent(employee -> {
            logger.error("Employee Contact Exists: {}", dto.getContactNumber());
            throw new NotFoundException("Employee Contact Exists");
        });
        String id = GenerateIds.getId("EMP");
        EmployeeEntity employee = EmployeeEntity
                .builder()
                .employeeCode(id.toLowerCase())
                .employeeName(dto.getEmployeeName().toLowerCase())
                .employeeProfilePic(image != null ? base64Encoder.convertBase64(image) : dto.getEmployeeProfilePic())
                .gender(dto.getGender())
                .status(dto.getStatus().toLowerCase())
                .designation(dto.getDesignation().toLowerCase())
                .role(dto.getRole())
                .dob(dto.getDob())
                .dateOfJoin(dto.getDateOfJoin())
                .branch(dto.getBranch().toLowerCase())
                .addressLine1(dto.getAddressLine1().toLowerCase())
                .addressLine2(dto.getAddressLine2().toLowerCase())
                .addressLine3(dto.getAddressLine3().toLowerCase())
                .addressLine4(dto.getAddressLine4().toLowerCase())
                .addressLine5(dto.getAddressLine5().toLowerCase())
                .contactNumber(dto.getContactNumber())
                .email(dto.getEmail().toLowerCase())
                .informInCaseOfEmergency(dto.getInformInCaseOfEmergency().toLowerCase())
                .emergencyContactNumber(dto.getEmergencyContactNumber())
                .build();

        logger.info("Employee Saved Id: {}", id);
        employeeDao.save(employee);
        return mapping.toEmployeeDTO(employee);
    }

    @Override
    public EmployeeDTO getEmployeeById(String code) {
        var byId = employeeDao.findById(code);
        if (byId.isPresent()) {
            logger.error("Get Employee : {}", code);
            return mapping.toEmployeeDTO(employeeDao.getReferenceById(code));
        }
        logger.error("Employee Not Found: {}", code);
        throw new NotFoundException("Employee Not Found");
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        logger.info("Get All Employees Request");
        return mapping.toEmployeeDTOList(employeeDao.findAll());
    }

    @Override
    public void deleteEmployeeById(String code) {
        logger.info("Delete Employee Request: {}", code);
        if (!employeeDao.existsById(code)) throw new NotFoundException("Employee Not Found");
        employeeDao.deleteById(code);
    }

    @Override
    public EmployeeDTO updateEmployee(String code, String emp, MultipartFile image) throws IOException{
        EmployeeDTO dto = json.readValue(emp, EmployeeDTO.class);
        Optional<EmployeeEntity> employee = employeeDao.findById(code);

        if (employee.isPresent()) {
            EmployeeEntity existEmployee = employee.get();

            existEmployee.setEmployeeName(dto.getEmployeeName().toLowerCase());
            existEmployee.setGender(dto.getGender());
            existEmployee.setStatus(dto.getStatus().toLowerCase());
            existEmployee.setDesignation(dto.getDesignation().toLowerCase());
            existEmployee.setRole(dto.getRole());
            existEmployee.setDob(dto.getDob());
            existEmployee.setDateOfJoin(dto.getDateOfJoin());
            existEmployee.setBranch(dto.getBranch().toLowerCase());
            existEmployee.setAddressLine1(dto.getAddressLine1().toLowerCase());
            existEmployee.setAddressLine2(dto.getAddressLine2().toLowerCase());
            existEmployee.setAddressLine3(dto.getAddressLine3().toLowerCase());
            existEmployee.setAddressLine4(dto.getAddressLine4().toLowerCase());
            existEmployee.setAddressLine5(dto.getAddressLine5().toLowerCase());
            existEmployee.setContactNumber(dto.getContactNumber());
            existEmployee.setEmail(dto.getEmail().toLowerCase());
            existEmployee.setInformInCaseOfEmergency(dto.getInformInCaseOfEmergency().toLowerCase());
            existEmployee.setEmergencyContactNumber(dto.getEmergencyContactNumber());

            if (image != null) {
                existEmployee.setEmployeeProfilePic(base64Encoder.convertBase64(image));
            }
            employeeDao.save(existEmployee);
            logger.info("Employee Updated");
            return mapping.toEmployeeDTO(existEmployee);
        } else {
            logger.error("Employee Not Found: {}", code);
            throw new NotFoundException("Employee Not Found");
        }
    }
}
