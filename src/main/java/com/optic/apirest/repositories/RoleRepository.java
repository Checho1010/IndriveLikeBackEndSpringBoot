package com.optic.apirest.repositories;

import com.optic.apirest.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, String> {
    boolean existsByName(String name);

//    @Query("SELECT r FROM Role r JOIN r.userHasRoles ur WHERE ur.user.id = :userId")
//    List<Role> findRolesByUserId(@Param("userId") Long userId);

    List<Role> findAllByUserHasRoles_User_Id(Long idUser);
}
