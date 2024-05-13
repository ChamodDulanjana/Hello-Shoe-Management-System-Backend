package com.chamoddulanjana.helloshoemanagementsystem.service.custom.impl;

import com.chamoddulanjana.helloshoemanagementsystem.dao.UserDao;
import com.chamoddulanjana.helloshoemanagementsystem.dto.UserDTO;
import com.chamoddulanjana.helloshoemanagementsystem.entity.custom.UserEntity;
import com.chamoddulanjana.helloshoemanagementsystem.exception.NotFoundException;
import com.chamoddulanjana.helloshoemanagementsystem.service.custom.UserService;
import com.chamoddulanjana.helloshoemanagementsystem.util.Mapping;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Mapping mapping;
    private final UserDao userDao;

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        return mapping.toUserDTO(userDao.save(mapping.toUserEntity(userDTO)));
    }

    @Override
    public UserDTO findUserById(String code) {
        if (!userDao.existsById(code)) throw new NotFoundException("User not found");
        return mapping.toUserDTO(userDao.getReferenceById(code));
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return mapping.toUserDTOList(userDao.findAll());
    }

    @Override
    public void deleteUser(String code) {
        if (!userDao.existsById(code)) throw new NotFoundException("User not found");
        userDao.deleteById(code);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, String code) {
        if (userDao.existsById(code)) throw new NotFoundException("User not found");
        var byId = userDao.findById(code);
        if (byId.isPresent()){
            byId.get().setEmail(userDTO.getEmail());
            byId.get().setPassword(userDTO.getPassword());
            byId.get().setRole(userDTO.getRole());
        }
        return mapping.toUserDTO(userDao.getReferenceById(code));
    }
}
