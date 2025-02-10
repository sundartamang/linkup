package com.linkup.auth.service;

import com.linkup.auth.dto.UsersDTO;
import java.util.List;

public interface UserService {
    UsersDTO registerUser(UsersDTO usersDTO);
    UsersDTO createUser(UsersDTO usersDTO);
    UsersDTO updateUser(UsersDTO usersDTO, Integer userId);
    UsersDTO getUserDetail(Integer userId);
    List<UsersDTO> getAllUsers();
    void deleteUser(Integer userId);
}
