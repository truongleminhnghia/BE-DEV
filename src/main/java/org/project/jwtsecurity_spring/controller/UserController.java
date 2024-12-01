package org.project.jwtsecurity_spring.controller;

import jakarta.validation.Valid;
import org.project.jwtsecurity_spring.dtos.requests.UserRegisterRequest;
import org.project.jwtsecurity_spring.dtos.requests.UserUpdateRequest;
import org.project.jwtsecurity_spring.dtos.responses.ApiResponse;
import org.project.jwtsecurity_spring.dtos.responses.UserResponse;
import org.project.jwtsecurity_spring.entities.User;
import org.project.jwtsecurity_spring.exception.AppException;
import org.project.jwtsecurity_spring.mapper.UserMapper;
import org.project.jwtsecurity_spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired private UserMapper userMapper;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserRegisterRequest request) {
        try {
            User user = userService.save(request);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, "Failed", null));
            }
            UserResponse userResponse = userMapper.toUserResponse(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(201, "Success", userResponse));
        } catch (AppException e) {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(e.getErrorCode().getCode().value(), e.getErrorCode().getMessage(), null));
        }


    }

//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/userId/{user_id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable("user_id") String id) {
        UserResponse userResponse = userMapper.toUserResponse(userService.getById(id));
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Success", userResponse));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse> getByEmail(@PathVariable("email") String email) {
        try {
            User user = userService.getByEmail(email);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(401, "Failed", null));
            }
            UserResponse userResponse = userMapper.toUserResponse(user);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Success", userResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(500, "Failed", e.getMessage()));
        }
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<ApiResponse> getAll() {
        List<UserResponse> userResponses = userService.getAll();
        if (userResponses == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(401, "Failed", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Success", userResponses));
    }

//    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update/{user_id}")
    public ResponseEntity<ApiResponse> update(@PathVariable("user_id") String id, @Valid @RequestBody UserUpdateRequest request) {
        User user = userService.update(id, request);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, "Failed", null));
        }
        UserResponse userResponse = userMapper.toUserResponse(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Success", userResponse));
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("user_id") String id) {
        boolean userResponse = userService.delete(id);
        if (userResponse)
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Success", null));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(200, "Failed", null));
    }
}
