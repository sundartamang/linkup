package com.linkup.auth.service.impl;

import com.linkup.auth.dto.UsersDTO;
import com.linkup.auth.entity.Role;
import com.linkup.auth.entity.Users;
import com.linkup.auth.repository.RoleRepo;
import com.linkup.auth.repository.UserRepo;
import com.linkup.auth.service.UserService;
import com.linkup.exceptions.ResourceNotFoundException;
import com.linkup.utils.AppConstant;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final RoleRepo roleRepo;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepo userRepo, ModelMapper modelMapper, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
        this.roleRepo = roleRepo;
    }

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UsersDTO registerUser(UsersDTO usersDTO) {
        this.checkForEmailTaken(usersDTO);
        Users users = this.modelMapper.map(usersDTO, Users.class);
        users.setPassword(encoder.encode(users.getPassword()));

        Role role = this.roleRepo.findById(AppConstant.NORMAL_USER).get();
        users.getRoles().add(role);

        Users savedUser = this.userRepo.save(users);
        logger.info("User registered with email: {}", usersDTO.getEmail());
        return this.modelMapper.map(savedUser, UsersDTO.class);
    }

    @Override
    public UsersDTO createUser(UsersDTO usersDTO) {
        this.checkForEmailTaken(usersDTO);
        Users users = this.modelMapper.map(usersDTO, Users.class);
        Users savedUser = this.userRepo.save(users);
        logger.info("User created with email: {}", usersDTO.getEmail());
        return this.modelMapper.map(savedUser, UsersDTO.class);
    }

    @Override
    public UsersDTO updateUser(UsersDTO usersDTO, Integer userId) {
        Users users = this.userRepo.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User ", " id ", userId));
        users.setName(usersDTO.getName());
        users.setEmail(usersDTO.getEmail());
        users.setPassword(usersDTO.getPassword());
        logger.info("User updated with ID: {}", userId);
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
        logger.info("User deleted with ID: {}", userId);
        this.userRepo.delete(users);
    }

    private void checkForEmailTaken(UsersDTO usersDTO){
        Optional<Users> existingUser = this.userRepo.findByEmail(usersDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new ResourceNotFoundException("User with email ", " already registered", usersDTO.getEmail());
        }
    }
}
