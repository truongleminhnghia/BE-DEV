package org.project.jwtsecurity_spring.configs;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.project.jwtsecurity_spring.entities.Role;
import org.project.jwtsecurity_spring.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Data
@Builder
public class CustomerUserDetail implements UserDetails {

    private String userID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String avatar;
    private Collection<? extends GrantedAuthority> authorities;

    public static CustomerUserDetail mapUserToUserDetail(User user) {
        Role role = user.getRole();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName().name());
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(authority);

        return CustomerUserDetail.builder()
                .userID(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phone(user.getPhone())
                .avatar(user.getAvatar())
                .authorities(roles)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
