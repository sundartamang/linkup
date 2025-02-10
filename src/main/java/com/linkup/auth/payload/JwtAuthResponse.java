package com.linkup.auth.payload;

import com.linkup.auth.dto.UsersDTO;
import lombok.Data;

@Data
public class JwtAuthResponse {
    private String token;
    private UsersDTO usersDTO;
}
