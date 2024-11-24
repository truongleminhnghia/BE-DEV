package org.project.jwtsecurity_spring;

import io.jsonwebtoken.Jwts;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

public class JwtSecretMakerTest {

    @Test
    public void generateJwtSecret() {
        SecretKey key = Jwts.SIG.HS512.key().build();
        String endcodeKey = DatatypeConverter.printHexBinary(key.getEncoded());
        System.out.printf("Key = [%s]", endcodeKey);
    }
}
