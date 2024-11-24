package org.project.jwtsecurity_spring.services;

import org.project.jwtsecurity_spring.dtos.enums.EnumRoleName;
import org.project.jwtsecurity_spring.entities.Role;

public interface RoleService {
    Role save(Role role);
    Role getRole(EnumRoleName roleName);

}
