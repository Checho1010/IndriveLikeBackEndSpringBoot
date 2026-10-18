package com.optic.apirest.models;

import com.optic.apirest.models.id.UserRoleId;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_has_roles")
public class UserHasRoles {

    @EmbeddedId
    private UserRoleId id = new UserRoleId();

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "id_role")
    private Role role;

    public UserHasRoles() {}

    public UserHasRoles(User user, Role role) {
        this.user = user;
        this.role = role;
        this.id = new UserRoleId(user.getId(), role.getId());
    }
}
