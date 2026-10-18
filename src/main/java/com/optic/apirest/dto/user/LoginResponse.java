package com.optic.apirest.dto.user;

import lombok.Data;

@Data
public class LoginResponse {

    private String token;
    private UserResponse user;
}
