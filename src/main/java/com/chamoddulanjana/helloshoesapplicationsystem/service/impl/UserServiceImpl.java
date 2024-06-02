package com.chamoddulanjana.helloshoesapplicationsystem.service.impl;

import com.chamoddulanjana.helloshoesapplicationsystem.exception.customExceptions.AlreadyExistException;
import com.chamoddulanjana.helloshoesapplicationsystem.repository.EmployeeRepository;
import com.chamoddulanjana.helloshoesapplicationsystem.repository.UserRepository;
import com.chamoddulanjana.helloshoesapplicationsystem.dto.resAndReq.LoginRequest;
import com.chamoddulanjana.helloshoesapplicationsystem.dto.resAndReq.LoginResponse;
import com.chamoddulanjana.helloshoesapplicationsystem.dto.resAndReq.RegisterRequest;
import com.chamoddulanjana.helloshoesapplicationsystem.entity.Employee;
import com.chamoddulanjana.helloshoesapplicationsystem.entity.User;
import com.chamoddulanjana.helloshoesapplicationsystem.enums.Role;
import com.chamoddulanjana.helloshoesapplicationsystem.service.JWTService;
import com.chamoddulanjana.helloshoesapplicationsystem.service.UserService;
import com.chamoddulanjana.helloshoesapplicationsystem.util.GenerateId;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmployeeRepository employeeRepository;

    @Override
    public void register(RegisterRequest registerRequest) {
        Optional<User> byEmail = userRepository.findByEmail(registerRequest.getEmail().toLowerCase());
        if (byEmail.isPresent()) {
            LOGGER.error("Email already exists");
            throw new AlreadyExistException("Email already exists");
        }
        Employee employee = employeeRepository.getEmployeeByEmail(registerRequest.getEmail().toLowerCase()).orElseThrow(() -> {
            LOGGER.error("Email does not exist");
            return new AlreadyExistException("Email does not exist");
        });

        User user = User
                .builder()
                .email(registerRequest.getEmail().toLowerCase())
                .id(GenerateId.getId("USR").toLowerCase())
                .role(Role.USER)
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        user.setRole(employee.getRole());
        user.setEmployee(employee);

        employeeRepository.save(employee);
        userRepository.save(user);
        LOGGER.info("User Registered");
    }


    @Override
    public LoginResponse authenticate(LoginRequest loginRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail().toLowerCase(), loginRequest.getPassword()));
            if (authenticate.isAuthenticated()) {
                String token = jwtService.generateToken(loginRequest.getEmail().toLowerCase());
                LOGGER.info("User Authenticated: {}", loginRequest.getEmail().toLowerCase());
                LOGGER.info("Token: {}", token);
                return LoginResponse.builder().token(token).role(authenticate.getAuthorities()).build();
            } else {
                LOGGER.error("Invalid Credentials");
                throw new BadCredentialsException("Invalid Credentials");
            }
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage().toUpperCase());
            throw new BadCredentialsException("Invalid Credentials");
        }
    }

}
