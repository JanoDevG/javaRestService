package cl.janodevg.restService.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {

    private static final String invalidEmail = "mail@mail";
    private static final String validEmail = "mail@gmail.com";
    @Test
    void isInvalid() {
        assertFalse(EmailValidator.isValid(invalidEmail));
    }

    @Test
    void isValid() {
        assertTrue(EmailValidator.isValid(validEmail));
    }


}