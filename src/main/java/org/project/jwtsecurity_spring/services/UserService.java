package org.project.jwtsecurity_spring.services;

import org.project.jwtsecurity_spring.configs.CustomerUserDetail;
import org.project.jwtsecurity_spring.dtos.enums.EnumRoleName;
import org.project.jwtsecurity_spring.dtos.enums.ErrorCode;
import org.project.jwtsecurity_spring.dtos.requests.UserRegisterRequest;
import org.project.jwtsecurity_spring.dtos.requests.UserUpdateRequest;
import org.project.jwtsecurity_spring.dtos.responses.UserResponse;
import org.project.jwtsecurity_spring.entities.Role;
import org.project.jwtsecurity_spring.entities.User;
import org.project.jwtsecurity_spring.exception.AppException;
import org.project.jwtsecurity_spring.mapper.UserMapper;
import org.project.jwtsecurity_spring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService, IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return CustomerUserDetail.mapUserToUserDetail(user);
        } else {
            throw new UsernameNotFoundException(email);
        }
    }

    @Override
    public UserResponse save(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.USER_EXISTED);
        User user = userMapper.toUser(request);
        Role role = null;
        if (request.getRoleName() != null) {
            if (request.getRoleName().equalsIgnoreCase("admin")
                    || request.getRoleName().equalsIgnoreCase("ROLE_ADMIN")) {
                role = roleService.getRole(EnumRoleName.ROLE_ADMIN);
            } else if (request.getRoleName().equalsIgnoreCase("staff")
                    || request.getRoleName().equalsIgnoreCase("ROLE_STAFF")) {
                role = roleService.getRole(EnumRoleName.ROLE_STAFF);
            } else {
                role = roleService.getRole(EnumRoleName.ROLE_USER);
            }
        } else {
            role = roleService.getRole(EnumRoleName.ROLE_USER);
        }
        if (role == null) throw new AppException(ErrorCode.ROLE_NOT_NULL);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse getByEmail(String email) {
        User userExisting = userRepository.findByEmail(email);
        if(userExisting == null) throw new AppException(ErrorCode.USER_DOES_NOT_EXIST);
        return userMapper.toUserResponse(userExisting);
    }

    @Override
    public User getById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new AppException(ErrorCode.USERID_NULL_OR_EMPTY);
        }
        CustomerUserDetail customer = (CustomerUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("customer login current: " + customer.getEmail() + " " + customer.getAuthorities());
        boolean isAdmin = customer.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        if (!customer.getUserID().equals(id) && !isAdmin) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_DOES_NOT_EXIST));
        return user;
    }


    @Override
    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();
        if(users == null || users.isEmpty()) throw new AppException(ErrorCode.LIST_EMPTY);
        return userMapper.toUserResponses(users);
    }

    @Override
    public UserResponse update(String id, UserUpdateRequest request) {
        if (id == null || id.trim().isEmpty()) {
            throw new AppException(ErrorCode.USERID_NULL_OR_EMPTY);
        }
        CustomerUserDetail customer = (CustomerUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("customer login current: " + customer.getEmail() + " " + customer.getAuthorities());
        boolean isAdmin = customer.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        if (!customer.getUserID().equals(id) && !isAdmin) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_DOES_NOT_EXIST));
        if(request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if(request.getLastName() != null) user.setLastName(request.getLastName());
        if(request.getPhone() != null) user.setPhone(request.getPhone());
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public boolean delete(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new AppException(ErrorCode.USERID_NULL_OR_EMPTY);
        }
        CustomerUserDetail customer = (CustomerUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("customer login current: " + customer.getEmail() + " " + customer.getAuthorities());
        boolean isAdmin = customer.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            throw new AppException(ErrorCode.FORBIDDEN);
        }
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_DOES_NOT_EXIST));
        userRepository.deleteById(user.getId());
        return true;
    }
}
