package cl.janodevg.restService.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorTest {

    private static final String validPassword = "PASSwrod123";
    private static final String invalidPassword = "password123";
    @Test
    void isValid() {
        assertTrue(PasswordValidator.isValid(validPassword));
    }

    @Test
    void isInvalid() {
        assertFalse(PasswordValidator.isValid(invalidPassword));
    }
}