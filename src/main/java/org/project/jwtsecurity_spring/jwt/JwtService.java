package org.project.jwtsecurity_spring.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.project.jwtsecurity_spring.configs.CustomerUserDetail;
import org.project.jwtsecurity_spring.dtos.enums.ErrorCode;
import org.project.jwtsecurity_spring.exception.AppException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET = "E007827940DB15A8380D0A2C12CFDBF58C92553A14D80611BDBF3C7CAC06C09B16BCDD6DDE82A5D616DB8C6EE2577243F4C24F36605EF478EFDB03DE3FCFE47C";

    private static final long VALIDITY = TimeUnit.MINUTES.toMillis(30);

    public String generateToken(CustomerUserDetail customerDetail) {
        Date date = new Date(System.currentTimeMillis());
        Date exp = new Date(System.currentTimeMillis() + VALIDITY);
        return Jwts.builder()
                .subject(customerDetail.getUsername())
                .claim("iss", "http://localhost:8080/jwt")
                .claim("userId", customerDetail.getUserID())
                .claim("email", customerDetail.getEmail())
                .claim("role", customerDetail.getAuthorities())
                .issuedAt(date)
                .expiration(exp)
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey() {
        byte[] decodeKey = Base64.getDecoder().decode(SECRET);
        return new SecretKeySpec(decodeKey, "HmacSHA256");
    }

    public String extractEmail(String token) {
        return getClaims(token, Claims::getSubject);
    }

    private <T> T getClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(
                Jwts.parser()
                        .verifyWith(generateKey())
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
        );
    }

    public boolean isTokenValid(String token) {
        if (extractEmail(token) != null && !isTokenExpired(token)) {
            return true;
        } else {
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }
    }


//    public boolean isTokenExpired(String token) {
//        return getClaims(token, Claims::getExpiration).before(new Date(System.currentTimeMillis()));
//    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = getClaims(token, Claims::getExpiration);
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }
}
