package com.chamoddulanjana.helloshoemanagementsystem.service.custom;

import com.chamoddulanjana.helloshoemanagementsystem.dto.UserDTO;
import com.chamoddulanjana.helloshoemanagementsystem.dto.reqAndRes.LoginRequest;
import com.chamoddulanjana.helloshoemanagementsystem.dto.reqAndRes.LoginResponse;
import com.chamoddulanjana.helloshoemanagementsystem.dto.reqAndRes.RegisterRequest;
import com.chamoddulanjana.helloshoemanagementsystem.service.SuperService;

import java.util.List;

public interface UserService extends SuperService {
    void register(RegisterRequest registerRequest);
    void updatePassword(RegisterRequest registerRequest);
    LoginResponse authenticate(LoginRequest loginRequest);

}
