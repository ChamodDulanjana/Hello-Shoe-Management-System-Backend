package com.chamoddulanjana.helloshoesapplicationsystem.api.auth;

import com.chamoddulanjana.helloshoesapplicationsystem.dto.resAndReq.LoginRequest;
import com.chamoddulanjana.helloshoesapplicationsystem.dto.resAndReq.LoginResponse;
import com.chamoddulanjana.helloshoesapplicationsystem.dto.resAndReq.RegisterRequest;
import com.chamoddulanjana.helloshoesapplicationsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/users")
@RequiredArgsConstructor
public class User {
    private final UserService userService;
    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(User.class);

    @PostMapping("/login")
    public LoginResponse authenticate(@Validated @RequestBody LoginRequest loginRequest) {
        LOGGER.info("User Login Request: {}", loginRequest);
        return userService.authenticate(loginRequest);
    }


    @PostMapping
    public void register(@Validated @RequestBody RegisterRequest registerRequest) {
        LOGGER.info("User Register Request: {}", registerRequest);
        userService.register(registerRequest);
    }

}
