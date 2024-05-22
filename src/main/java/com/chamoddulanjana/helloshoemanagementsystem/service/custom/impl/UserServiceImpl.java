package com.chamoddulanjana.helloshoemanagementsystem.service.custom.impl;

import com.chamoddulanjana.helloshoemanagementsystem.dao.EmployeeDao;
import com.chamoddulanjana.helloshoemanagementsystem.dao.UserDao;
import com.chamoddulanjana.helloshoemanagementsystem.dto.UserDTO;
import com.chamoddulanjana.helloshoemanagementsystem.dto.reqAndRes.LoginRequest;
import com.chamoddulanjana.helloshoemanagementsystem.dto.reqAndRes.LoginResponse;
import com.chamoddulanjana.helloshoemanagementsystem.dto.reqAndRes.RegisterRequest;
import com.chamoddulanjana.helloshoemanagementsystem.entity.Role;
import com.chamoddulanjana.helloshoemanagementsystem.entity.custom.EmployeeEntity;
import com.chamoddulanjana.helloshoemanagementsystem.entity.custom.UserEntity;
import com.chamoddulanjana.helloshoemanagementsystem.exception.AlreadyExistException;
import com.chamoddulanjana.helloshoemanagementsystem.exception.NotFoundException;
import com.chamoddulanjana.helloshoemanagementsystem.service.JWTService;
import com.chamoddulanjana.helloshoemanagementsystem.service.custom.UserService;
import com.chamoddulanjana.helloshoemanagementsystem.util.GenerateIds;
import com.chamoddulanjana.helloshoemanagementsystem.util.Mapping;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmployeeDao employeeDao;

    @Override
    public void register(RegisterRequest registerRequest) {
        Optional<UserEntity> byEmail = userDao.findByEmail(registerRequest.getEmail().toLowerCase());
        if (byEmail.isPresent()) {
            logger.error("Email already exists");
            throw new AlreadyExistException("Email already exists");
        }
        EmployeeEntity employee = employeeDao.findEmployeeByEmail(registerRequest.getEmail().toLowerCase()).orElseThrow(() -> {
            logger.error("Email does not exist");
            return new AlreadyExistException("Email does not exist");
        });

        UserEntity user = UserEntity
                .builder()
                .email(registerRequest.getEmail().toLowerCase())
                .id(GenerateIds.getId("USR").toLowerCase())
                .role(Role.USER)
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        user.setRole(employee.getRole());
        user.setEmployee(employee);

        employeeDao.save(employee);
        userDao.save(user);
        logger.info("User Registered");
    }

    @Override
    public void updatePassword(RegisterRequest registerRequest) {
        Optional<UserEntity> byEmail = userDao.findByEmail(registerRequest.getEmail());
        if (byEmail.isPresent()) {
            UserEntity user = byEmail.get();
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            userDao.save(user);
            logger.info("Password Updated ID: {}", user.getId());
        } else {
            logger.error("Email does not exist");
            throw new AlreadyExistException("Email does not exist");
        }
    }

    @Override
    public LoginResponse authenticate(LoginRequest loginRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail().toLowerCase(), loginRequest.getPassword()));
            if (authenticate.isAuthenticated()) {
                String token = jwtService.generateToken(loginRequest.getEmail().toLowerCase());
                logger.info("User Authenticated: {}", loginRequest.getEmail().toLowerCase());
                logger.info("Token: {}", token);
                return LoginResponse.builder().token(token).role(authenticate.getAuthorities()).build();
            } else {
                logger.error("Invalid Credentials");
                throw new BadCredentialsException("Invalid Credentials");
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage().toUpperCase());
            throw new BadCredentialsException("Invalid Credentials");
        }
    }

    }
}
