package com.chamoddulanjana.helloshoesapplicationsystem.service;

import com.chamoddulanjana.helloshoesapplicationsystem.dto.resAndReq.LoginRequest;
import com.chamoddulanjana.helloshoesapplicationsystem.dto.resAndReq.LoginResponse;
import com.chamoddulanjana.helloshoesapplicationsystem.dto.resAndReq.RegisterRequest;

public interface UserService {
    void register(RegisterRequest registerRequest);
    LoginResponse authenticate(LoginRequest loginRequest);
}
