package com.appswave.task.service;

import com.appswave.task.common.constants.Constants;
import com.appswave.task.mapper.UserMapper;
import com.appswave.task.model.dtos.UserDTO;
import com.appswave.task.model.entity.User;
import com.appswave.task.model.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserDTO> getAllUsers() {
        return userMapper.toListDTO(userRepository.findAll());
    }

    public UserDTO getUserById(Long id) {
        return userMapper.toDTO(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id)));
    }

    public UserDTO createUser(UserDTO user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toDTO(userRepository.save(userMapper.toEntity(user)));
    }

    public UserDTO updateUser(Long id, UserDTO user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(Constants.ENTITY_NOT_FOUND));
        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setDateOfBirth(user.getDateOfBirth());
        existingUser.setRole(user.getRole());
        return userMapper.toDTO(userRepository.save(existingUser));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
