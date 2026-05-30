package com.example.demo.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
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
public class JwtService {
    @Value("${jwt.secret-key}")
    @NonFinal
    String secretKey;
    @NonFinal
    private static final long ACCESS_TOKEN_EXPIRED =  1000 * 60 * 15; // 15 minutes

    private SecretKey getSigningKey() {
        return new SecretKeySpec(
            secretKey.getBytes(StandardCharsets.UTF_8),
            "HmacSHA256"
        );
    }
    public String generateToken(User user, long expiration) {
        try{
            JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256).type(JOSEObjectType.JWT).build();
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(Long.toString(user.getId()))
                    .issuer("chat-app")
                    .claim("roles", user.getRoles().stream().map(Role::getName).toList())
                    .expirationTime(new Date(System.currentTimeMillis() + expiration))
                    .jwtID(java.util.UUID.randomUUID().toString())
                    .issueTime(new Date())
                    .build();
            
                SignedJWT signedJWT = new SignedJWT(header, claimsSet);
                signedJWT.sign(new MACSigner(getSigningKey()));
                return signedJWT.serialize();
        }
        catch(Exception e){
            throw new RuntimeException("Error generating token", e);
        }
    }

    public SignedJWT verifyToken(String token) {
        try {
           SignedJWT signedJWT = SignedJWT.parse(token);

            if (!signedJWT.getHeader()
                .getAlgorithm()
                .equals(JWSAlgorithm.HS256)) {

            throw new RuntimeException("Invalid algorithm");
            }

            JWSVerifier verifier =
                new MACVerifier(getSigningKey());

             boolean verified = signedJWT.verify(verifier);

            if (!verified) {
                throw new RuntimeException("Invalid token signature");
            }

            Date expiration =
                    signedJWT.getJWTClaimsSet()
                            .getExpirationTime();

            if (expiration.before(new Date())) {
                throw new RuntimeException("Token has expired");
            }

            return signedJWT;
            
        } catch (Exception e) {
            throw new RuntimeException("Error verifying token", e);
        }
    }

    public String getSubject(String token) {
        try {
            SignedJWT signedJWT = verifyToken(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Error getting subject from token", e);
        }
    }
}
