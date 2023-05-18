package com.infodev.sanimaotp.dao;

import com.infodev.sanimaotp.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    @Query(value="Select * from Role where name=?1",nativeQuery = true)
    public Role findRoleByRoleName(String name);

    @Query(nativeQuery = true, value = "select r.* from user_credentials uc left join user_role ur on uc.id = ur.user_id\n" +
            "left join role r on ur.role_id = r.id  where uc.username =?1")
    Optional<Role> getRoleByUsername(String username);

    @Modifying
    @Query(nativeQuery = true,
    value = "delete from roles_modules where role_id = ?1")
    void deleteExistingModulesFromRole(Integer roleId);

    @Query(nativeQuery = true,
            value = "select * from role where status = 1")
    List<Role> fetchAll();
}
