package com.chamoddulanjana.helloshoemanagementsystem.controller;

import com.chamoddulanjana.helloshoemanagementsystem.dto.UserDTO;
import com.chamoddulanjana.helloshoemanagementsystem.exception.NotFoundException;
import com.chamoddulanjana.helloshoemanagementsystem.service.custom.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Repository
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class User {

    private final UserService userService;

    @GetMapping("/health")
    public String healthCheck(){
        return "OK";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveUser(@Validated @RequestBody UserDTO user, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldErrors().get(0).getDefaultMessage());
        }
        try {
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(" User saved successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error! | Customer saved successfully!  \\nMore Details\\n\"+e.getMessage()" +e.getMessage());
        }
    }

    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserById(@PathVariable String code){
        try {
            return ResponseEntity.ok(userService.findUserById(code));
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }
    }
}
