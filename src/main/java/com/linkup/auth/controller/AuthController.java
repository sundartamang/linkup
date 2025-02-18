package com.linkup.auth.controller;

import com.linkup.auth.dto.UsersDTO;
import com.linkup.auth.payload.JwtAuthRequest;
import com.linkup.auth.payload.JwtAuthResponse;
import com.linkup.auth.service.AuthService;
import com.linkup.auth.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UsersDTO> registerUser(@RequestBody UsersDTO userDto){
        UsersDTO registerNewUSer = this.userService.registerUser(userDto);
        return new ResponseEntity<UsersDTO>(registerNewUSer, HttpStatus.CREATED);
    }

    @PostMapping("/user-login")
    public ResponseEntity<JwtAuthResponse> createToken(
            @RequestBody JwtAuthRequest jwtAuthRequest) throws Exception{
        JwtAuthResponse jwtAuthResponse = this.authService.authenticateUser(jwtAuthRequest);
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }
}
