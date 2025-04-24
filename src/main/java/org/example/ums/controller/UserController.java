package org.example.ums.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ums.dto.user.UserPatchRequest;
import org.example.ums.dto.user.UserPostRequest;
import org.example.ums.dto.user.UserResponse;
import org.example.ums.mapper.UserMapper;
import org.example.ums.model.Role;
import org.example.ums.model.User;
import org.example.ums.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.example.ums.util.RequestConstants.USER_ID_REQUEST_HEADER;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor

@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;

    UserMapper userMapper;

    @GetMapping
    public List<UserResponse> findAll() {
        return userMapper.toDto(
                userService.findAll());
    }

    @GetMapping("/{id}")
    public UserResponse findById(int userId) {
        return userMapper.toDto(
                userService.findById(userId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody UserPostRequest userPostRequest) {
        User user = userMapper.fromDto(userPostRequest);

        return userMapper.toDto(
                userService.create(user));
    }

    @PatchMapping("/{id}")
    public UserResponse updateCredentials(@Valid @RequestBody UserPatchRequest userPatchRequest,
                               @PathVariable(name = "id") int updateUserId,
                               @RequestHeader(USER_ID_REQUEST_HEADER) Integer requesterId) {
        User user = userMapper.fromDto(userPatchRequest);

        return userMapper.toDto(
                userService.updateCredentials(user, updateUserId, requesterId));
    }

    @PatchMapping("/role/{id}")
    public UserResponse updateRole(@RequestParam Role role,
                                   @PathVariable(name = "id") int updateUserId,
                                   @RequestHeader(USER_ID_REQUEST_HEADER) Integer requesterId) {
        return userMapper.toDto(
                userService.updateRole(role, updateUserId, requesterId));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(name = "id") int deleteUserId,
                           @RequestHeader(USER_ID_REQUEST_HEADER) Integer requesterId) {
        userService.deleteById(deleteUserId, requesterId);
    }
}
