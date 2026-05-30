package com.example.demo.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.enums.ErrorCode;
import com.example.demo.exception.AppException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final Cloudinary cloudinary;

    public void CreateUser(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        User user = userMapper.toEntity(request, null);
        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        Role memberRole = roleRepository
                .findByName("USER")
                .orElseThrow();

        user.setRoles(Set.of(memberRole));
        userRepository.save(user);
    }

    public UserResponse updateUser(Long id, UserRequest request, MultipartFile avatarFile) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setBio(request.getBio());
        if (avatarFile != null && !avatarFile.isEmpty()) {
           try {
                Map<?, ?> uploadResult = cloudinary.uploader().upload(
                        avatarFile.getBytes(),
                        ObjectUtils.asMap("folder", "chat-app")
                );
                String fileUrl = uploadResult.get("secure_url").toString();
                user.setAvatarUrl(fileUrl);
            } catch (IOException e) {
                throw new RuntimeException("Upload avatar failed", e);
            }
        }
         if (request.getRoleIds() != null) {
            Set<Role> roles = roleRepository.findAllById(request.getRoleIds()).stream().collect(java.util.stream.Collectors.toSet());
            user.setRoles(roles);
        }
        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toResponse(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        userRepository.deleteById(id);
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<User> getUsersByIds(Set<Long> ids) {
        return userRepository.findByIdIn(ids);
    }
}
