package com.chamoddulanjana.helloshoemanagementsystem;

import jakarta.servlet.annotation.MultipartConfig;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MultipartConfig(
        location = "D:\\AAD Module 65\\--- SPRINGBOOT ---\\FINAL PROJECT\\HelloShoeManagementSystem\\src\\main\\java\\com\\chamoddulanjana\\helloshoemanagementsystem\\tmp",
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50,
        fileSizeThreshold = 1024 * 1024 * 5
)
public class HelloShoeManagementSystemApplication {

    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloShoeManagementSystemApplication.class, args);
    }

}
