package com.optic.apirest.models.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleId implements Serializable {

    @Column(name = "id_user")
    private Long userId;

    @Column(name = "id_rol")
    private String roleId;

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (!(o instanceof UserRoleId)) return false;
        UserRoleId userRoleId = (UserRoleId) o;
        return Objects.equals(userId, userRoleId.userId) && Objects.equals(roleId, userRoleId.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId);
    }


}
