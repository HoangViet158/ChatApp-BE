package com.example.demo.mapper;
import com.example.demo.dto.request.RefreshTokenRequest;
import com.example.demo.dto.response.RefreshTokenResponse;
import com.example.demo.entity.RefreshToken;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RefreshTokenMapper {

    // =========================
    // Request -> Entity
    // =========================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "createdAt", ignore = true)
    RefreshToken toEntity(
            RefreshTokenRequest request,
            User user
    );

    // =========================
    // Entity -> Response
    // =========================
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    RefreshTokenResponse toResponse(
            RefreshToken refreshToken
    );
}