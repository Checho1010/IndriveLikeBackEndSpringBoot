package com.optic.apirest.repositories;

import com.optic.apirest.models.UserHasRoles;
import com.optic.apirest.models.id.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHasRolesRepository extends JpaRepository<UserHasRoles, UserRoleId> {
}
