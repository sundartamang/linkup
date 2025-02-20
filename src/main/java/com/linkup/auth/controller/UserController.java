package com.linkup.auth.controller;

import com.linkup.auth.dto.UsersDTO;
import com.linkup.auth.payload.ApiResponse;
import com.linkup.auth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsersDTO> createUser(@Valid @RequestBody UsersDTO userDto) {
        UsersDTO createdUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsersDTO> updateUSer(@Valid @RequestBody UsersDTO userDto, @PathVariable Integer userId) {
        UsersDTO updatedUser = this.userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
        this.userService.deleteUser(userId);
        return ResponseEntity.ok(new ApiResponse("User deleted successfully", true));
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsersDTO>> getAllUsers() {
        List<UsersDTO> userData = this.userService.getAllUsers();
        return ResponseEntity.ok(userData);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsersDTO> getSingleUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(this.userService.getUserDetail(userId));
    }
}
