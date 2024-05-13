package com.chamoddulanjana.helloshoemanagementsystem.service.custom;

import com.chamoddulanjana.helloshoemanagementsystem.dto.UserDTO;
import com.chamoddulanjana.helloshoemanagementsystem.service.SuperService;

import java.util.List;

public interface UserService extends SuperService {
    UserDTO saveUser(UserDTO userDTO);
    UserDTO findUserById(String code);
    List<UserDTO> findAllUsers();
    void deleteUser(String code);
    UserDTO updateUser(UserDTO userDTO, String code);
}
