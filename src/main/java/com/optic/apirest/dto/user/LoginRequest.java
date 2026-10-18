package com.optic.apirest.dto.user;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;
}
