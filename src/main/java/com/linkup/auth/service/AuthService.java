package com.linkup.auth.service;

import com.linkup.auth.dto.UsersDTO;
import com.linkup.auth.entity.Users;
import com.linkup.auth.payload.JwtAuthRequest;
import com.linkup.auth.payload.JwtAuthResponse;
import com.linkup.auth.repository.UserRepo;
import com.linkup.exceptions.ApiException;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenService jwtTokenService;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    public AuthService(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtTokenService jwtTokenService,
            UserRepo userRepo,
            ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenService = jwtTokenService;
        this.userRepo = userRepo;
        this.modelMapper = modelMapper;
    }

    public JwtAuthResponse authenticateUser(JwtAuthRequest jwtAuthRequest) {

        this.authenticate(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
        String token = jwtTokenService.generateToken(userDetails);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);

        Optional<Users> user = userRepo.findByEmail(jwtAuthRequest.getUsername());
        UsersDTO userDto = modelMapper.map(user, UsersDTO.class);
        response.setUsersDTO(userDto);

        return response;
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new ApiException("Invalid username or password");
        }
    }
}
