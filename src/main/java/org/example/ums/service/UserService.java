package org.example.ums.service;

import jakarta.validation.ValidationException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.ums.exception.ForbiddenAccessException;
import org.example.ums.exception.NotFoundException;
import org.example.ums.model.Role;
import org.example.ums.model.User;
import org.example.ums.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor

@Service
public class UserService {
    UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int userId) {
        return checkIfUserExistsById(userId);
    }

    public User create(User user) {
        user.setRole(Role.STUDENT);

        userRepository.save(user);

        return user;
    }

    public User updateCredentials(User updateUser, int updateUserId, int requesterId) {
        User requester = checkIfUserExistsById(requesterId);

        // if requester and user that is being updated are equal,
        // no need to issue another call to DB
        User oldUser;
        if (updateUserId == requesterId) {
            oldUser = requester;
        } else {
            oldUser = checkIfUserExistsById(updateUserId);
        }

        // Check if password is valid
        if (!oldUser.getPassword().equals(updateUser.getPassword())) {
            throw new ValidationException("Incorrect password.");
        }

        if (updateUserId != requesterId && requester.getRole() != Role.ADMIN) {
            throw new ForbiddenAccessException("Insufficient rights to proceed.");
        }

        updateUser.setId(updateUserId);


        //Leave the old values, if new ones are not specified
        if (updateUser.getName() == null || updateUser.getName().isBlank()) {
            updateUser.setName(oldUser.getName());
        }

        if (updateUser.getEmail() == null || updateUser.getName().isBlank()) {
            updateUser.setEmail(oldUser.getEmail());
        }


        userRepository.save(updateUser);

        return updateUser;
    }

    public User updateRole(Role role, int updateUserId, int requesterId) {
        User requester = checkIfUserExistsById(requesterId);

        if (requester.getRole() != Role.ADMIN) {
            throw new ForbiddenAccessException("Insufficient rights to proceed.");
        }


        User updateUser;
        if (updateUserId == requesterId) {
            updateUser = requester;
        } else {
            updateUser = checkIfUserExistsById(updateUserId);
        }

        updateUser.setRole(role);

        userRepository.save(updateUser);

        return updateUser;
    }

    public void deleteById(int deleteUserId, int requesterId) {
        User requester = checkIfUserExistsById(requesterId);

        if (deleteUserId != requesterId && requester.getRole() != Role.ADMIN) {
            throw new ForbiddenAccessException("Insufficient rights to proceed.");
        }

        userRepository.deleteById(deleteUserId);
    }

    private User checkIfUserExistsById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with '%d' id not found.", userId)
                ));
    }
}
