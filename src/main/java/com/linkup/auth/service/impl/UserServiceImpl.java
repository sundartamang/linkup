package com.linkup.auth.service.impl;

import com.linkup.auth.dto.UsersDTO;
import com.linkup.auth.entity.Users;
import com.linkup.auth.repository.UserRepo;
import com.linkup.auth.service.UserService;
import com.linkup.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UsersDTO registerUser(UsersDTO usersDTO) {
        this.checkForEmailTaken(usersDTO);
        Users users = this.modelMapper.map(usersDTO, Users.class);
        users.setPassword(encoder.encode(users.getPassword()));
        return this.modelMapper.map(users, UsersDTO.class);
    }

    @Override
    public UsersDTO createUser(UsersDTO usersDTO) {
        this.checkForEmailTaken(usersDTO);
        Users users = this.modelMapper.map(usersDTO, Users.class);
        Users savedUser = this.userRepo.save(users);
        return this.modelMapper.map(savedUser, UsersDTO.class);
    }

    @Override
    public UsersDTO updateUser(UsersDTO usersDTO, Integer userId) {
        Users users = this.userRepo.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User ", " id ", userId));
        users.setName(usersDTO.getName());
        users.setEmail(usersDTO.getEmail());
        users.setPassword(usersDTO.getPassword());
        return this.modelMapper.map(users, UsersDTO.class);
    }

    @Override
    public UsersDTO getUserDetail(Integer userId) {
        Users users = this.userRepo.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User ", " id ", userId));
        return this.modelMapper.map(users, UsersDTO.class);
    }

    @Override
    public List<UsersDTO> getAllUsers() {
        List<Users> users = this.userRepo.findAll();
        return users.stream()
                .map(user -> this.modelMapper.map(user, UsersDTO.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer userId) {
        Users users = this.userRepo.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User ", " id ", userId));
        this.userRepo.delete(users);
    }

    private void checkForEmailTaken(UsersDTO usersDTO){
        Optional<Users> existingUser = this.userRepo.findByEmail(usersDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new ResourceNotFoundException("User with email ", " already registered", usersDTO.getEmail());
        }
    }
}
