package org.project.jwtsecurity_spring.repositories;

import org.project.jwtsecurity_spring.dtos.enums.EnumRoleName;
import org.project.jwtsecurity_spring.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByRoleName(EnumRoleName roleName);
}
