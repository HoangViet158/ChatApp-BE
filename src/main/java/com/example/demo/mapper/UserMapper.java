package com.example.demo.mapper;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(
    componentModel = "spring",
    uses = {
        RoleMapper.class
    }
)
public interface UserMapper {

    // =========================
    // Request -> Entity
    // =========================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isOnline", ignore = true)
    User toEntity(
            UserRequest request,
            Set<Role> roles
    );

    // =========================
    // Entity -> Response
    // =========================
    UserResponse toResponse(User user);
}