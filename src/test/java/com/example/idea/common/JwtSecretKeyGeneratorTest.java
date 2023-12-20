package com.example.idea.common;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.SecureRandom;
import java.util.Base64;
import org.junit.jupiter.api.Test;

/**
 * SecureRandom을 사용하여 JWT 시크릿 키를 생성하는 테스트 케이스
 *
 * - 시크릿 키는 최소 50자 이상 필요.
 * - HS 512 algorithm에서 시크릿 키 : 64Byte 이상
 * - SecureRandom을 사용하여 64바이트의 랜덤 데이터를 생성하고,
 * - Base64로 인코딩하여 시크릿 키를 출력
 */
public class JwtSecretKeyGeneratorTest {
    @Test
    public void generateJwtSecretKey() {
        // give
        byte[] secret = new byte[64];

        // when
        // Generate a 64-byte secret key
        new SecureRandom().nextBytes(secret);
        String encodedSecret = Base64.getEncoder().encodeToString(secret);

        // then
        // Check the length of the encoded secret
        // The length can vary due to Base64 encoding, but it should be more than 86 characters
        assertTrue(encodedSecret.length() > 86);
        System.out.println(encodedSecret);
    }
}
