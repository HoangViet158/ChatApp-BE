package com.example.demo.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Set;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.RefreshTokenRequest;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.AuthResponse;
import com.example.demo.entity.RefreshToken;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.enums.ErrorCode;
import com.example.demo.enums.MemberRole;
import com.example.demo.exception.AppException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CustomUserDetailService;
import com.example.demo.security.JwtService;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class AuthService {
    AuthenticationManager authenticationManager;
    PasswordEncoder passwordEncoder;
    CustomUserDetailService customUserDetailService;
    RefreshTokenService refreshTokenService;
    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    JwtService jwtService;
    @NonFinal
    @Value("${jwt.secret-key}")
    String secretKey;    
    @NonFinal
    private static final long ACCESS_TOKEN_EXPIRED =  1000 * 60 * 15; // 15 minutes

    // private SecretKey getSigningKey() {
    //     return new SecretKeySpec(
    //         secretKey.getBytes(StandardCharsets.UTF_8),
    //         "HmacSHA256"
    //     );
    // }
    // private String generateToken(User user, long expiration) {
    //     try{
    //         JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256).type(JOSEObjectType.JWT).build();
    //         JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
    //                 .subject(Long.toString(user.getId()))
    //                 .issuer("chat-app")
    //                 .claim("roles", user.getRoles().stream().map(Role::getName).toList())
    //                 .expirationTime(new Date(System.currentTimeMillis() + expiration))
    //                 .jwtID(java.util.UUID.randomUUID().toString())
    //                 .issueTime(new Date())
    //                 .build();
            
    //             SignedJWT signedJWT = new SignedJWT(header, claimsSet);
    //             signedJWT.sign(new MACSigner(getSigningKey()));
    //             return signedJWT.serialize();
    //     }
    //     catch(Exception e){
    //         throw new RuntimeException("Error generating token", e);
    //     }
    // }

    // public SignedJWT verifyToken(String token) {
    //     try {
    //        SignedJWT signedJWT = SignedJWT.parse(token);

    //         if (!signedJWT.getHeader()
    //             .getAlgorithm()
    //             .equals(JWSAlgorithm.HS256)) {

    //         throw new RuntimeException("Invalid algorithm");
    //         }

    //         JWSVerifier verifier =
    //             new MACVerifier(getSigningKey());

    //          boolean verified = signedJWT.verify(verifier);

    //         if (!verified) {
    //             throw new RuntimeException("Invalid token signature");
    //         }

    //         Date expiration =
    //                 signedJWT.getJWTClaimsSet()
    //                         .getExpirationTime();

    //         if (expiration.before(new Date())) {
    //             throw new RuntimeException("Token has expired");
    //         }

    //         return signedJWT;
            
    //     } catch (Exception e) {
    //         throw new RuntimeException("Error verifying token", e);
    //     }
    // }

    @Transactional
    public AuthResponse refresh(String token) {

    RefreshToken refreshToken =
            refreshTokenService
                    .verifyRefreshToken(token);

    User user = userRepository.findById(refreshToken.getUser().getId())
        .orElseThrow();

    refreshTokenService.revokeToken(refreshToken.getToken());

    String newAccessToken = jwtService.generateToken(user, ACCESS_TOKEN_EXPIRED);

    RefreshToken newRefreshToken =
            refreshTokenService.createRefreshToken(user);

    return AuthResponse.builder()
            .accessToken(newAccessToken)
            .refreshToken(newRefreshToken.getToken())
            .user(userMapper.toResponse(user))
            .build();
    }

    public AuthResponse login(String email, String password) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        User user = customUserDetailService.loadUserByUsername(email).getUser();

        String accessToken = jwtService.generateToken(user, ACCESS_TOKEN_EXPIRED);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .user(userMapper.toResponse(user))
                .build();
    }
    
    public void logout(String refreshToken) {
        refreshTokenService.revokeToken(refreshToken);
    }

    public void register(UserRequest request) {

        User user = userMapper.toEntity(request, null);

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        Role memberRole = roleRepository
                .findByName("USER")
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        user.setRoles(Set.of(memberRole));
        userRepository.save(user);
    }

    // public String getSubject(String token) {
    //     try {
    //         SignedJWT signedJWT = jwtService.verifyToken(token);
    //         return signedJWT.getJWTClaimsSet().getSubject();
    //     } catch (Exception e) {
    //         throw new RuntimeException("Error getting subject from token", e);
    //     }
    // }
}
