package com.chamoddulanjana.helloshoemanagementsystem.controller;

import com.chamoddulanjana.helloshoemanagementsystem.dto.reqAndRes.LoginRequest;
import com.chamoddulanjana.helloshoemanagementsystem.dto.reqAndRes.LoginResponse;
import com.chamoddulanjana.helloshoemanagementsystem.dto.reqAndRes.RegisterRequest;
import com.chamoddulanjana.helloshoemanagementsystem.service.custom.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/auth/users")
@RequiredArgsConstructor
public class User {

    private final UserService userService;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(User.class);

    @PostMapping("/login")
    public LoginResponse authenticate(@Validated @RequestBody LoginRequest loginRequest) {
        logger.info("User Login Request: {}", loginRequest);
        return userService.authenticate(loginRequest);
    }

    @PostMapping
    public void register(@Validated @RequestBody RegisterRequest registerRequest) {
        logger.info("User Register Request: {}", registerRequest);
        userService.register(registerRequest);
    }

    @PutMapping
    public void updatePassword(@Validated @RequestBody RegisterRequest registerRequest) {
        logger.info("Update Password Request: {}", registerRequest);
        userService.updatePassword(registerRequest);
    }
}
