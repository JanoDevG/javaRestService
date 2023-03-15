package cl.janodevg.restService.configurations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {


    String string32Byte = "0123456789abcdefasdasd012345sdfsdf6789abcdef";



    @Test
    void generateTokenWithSHA256() {
        assertEquals(JwtUtil.generateASecureKey().getBytes().length, string32Byte.getBytes().length);
    }

    @Test
    void isAValidToken() {
        assertTrue(JwtUtil.validateToken(JwtUtil.generateToken("ale")));
    }
}