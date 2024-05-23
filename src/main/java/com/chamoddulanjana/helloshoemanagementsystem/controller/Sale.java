package com.chamoddulanjana.helloshoemanagementsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/sales")
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class Sale {

    @GetMapping("/health")
    public String healthCheck(){
        return "Sales Health Check";
    }
}
