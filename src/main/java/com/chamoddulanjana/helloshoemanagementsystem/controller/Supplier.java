package com.chamoddulanjana.helloshoemanagementsystem.controller;

import com.chamoddulanjana.helloshoemanagementsystem.dto.SupplierDTO;
import com.chamoddulanjana.helloshoemanagementsystem.dto.UserDTO;
import com.chamoddulanjana.helloshoemanagementsystem.exception.NotFoundException;
import com.chamoddulanjana.helloshoemanagementsystem.service.custom.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveSupplier(@Validated @RequestBody SupplierDTO supplierDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        try {
            supplierService.saveSupplier(supplierDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(" Supplier saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error! | Supplier saved successfully!  \\nMore Details\\n\"+e.getMessage()" +e.getMessage());
        }
    }

    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSupplierById(@PathVariable String code){
        try {
            return ResponseEntity.ok(supplierService.getSupplierById(code));
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier not found!");
        }
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SupplierDTO> getAllSupplier(){
        return supplierService.getAllSuppliers();
    }

    @DeleteMapping("/{code}")
    public void deleteSupplier(@PathVariable String code){
        supplierService.deleteSupplier(code);
    }

    @PutMapping("/{code}")
    public ResponseEntity<?> updateSupplier(@Validated @RequestBody SupplierDTO supplier, @PathVariable String code, BindingResult bindingResult ){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        SupplierDTO supplierDTO = supplierService.updateSupplier(supplier, code);
        return supplierDTO != null ? ResponseEntity.ok(supplierDTO) : ResponseEntity.status(HttpStatus.NOT_FOUND).body("Supplier not found!");
    }
}
