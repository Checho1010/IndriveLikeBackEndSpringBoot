package com.optic.apirest.dto.user;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateUserRequest {

    private String name;
    private String lastName;
    private String phone;
    private MultipartFile file;
}
