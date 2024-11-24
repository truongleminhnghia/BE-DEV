package org.project.jwtsecurity_spring.services;

import org.project.jwtsecurity_spring.dtos.enums.EnumRoleName;
import org.project.jwtsecurity_spring.entities.Role;
import org.project.jwtsecurity_spring.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImp implements RoleService {

    @Autowired private RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getRole(EnumRoleName roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
