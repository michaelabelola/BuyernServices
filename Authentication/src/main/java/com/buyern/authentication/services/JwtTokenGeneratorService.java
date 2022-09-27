package com.buyern.authentication.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.buyern.authentication.models.UserAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class JwtTokenGeneratorService {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${user.access.token.ttl}")
    private String userAccessToken;

    /**
     * <h3>Generate new User {@link com.auth0.jwt.JWT JWT}</h3>
     *
     * @return {@link java.lang.String String} format of signed {@link com.auth0.jwt.JWT JWT}
     */
    public String generateUserAccessToken(UserAuth userAuth) {
        return JWT.create()
                .withJWTId(userAuth.getUid())
                .withSubject("userAccess")
                .withHeader(Map.of("version", "1.0.0"))
                .withClaim("entityRegistration", true)
                //expires in 31 days
                .withExpiresAt(new Date(System.currentTimeMillis() + 2678400000L))
                .sign(Algorithm.HMAC256(secret));
    }
}
