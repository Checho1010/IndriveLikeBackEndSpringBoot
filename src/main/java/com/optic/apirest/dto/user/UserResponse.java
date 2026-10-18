package com.optic.apirest.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.optic.apirest.dto.role.RoleDTO;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    public Long id;
    public String name;
    public String lastName;
    public String email;
    public String phone;
    public String image;

    @JsonProperty("notification_token")
    public String notificationToken;

    List<RoleDTO> roles;
}
