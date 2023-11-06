package cl.janodevg.restService.services.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MinCharacterPasswordValidation implements ConstraintValidator<MinCharacterPasswordValidationAnnotation, String> {

    private static final Pattern PASSWORD_REGEX = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d.*\\d).+$");

    @Override
    public void initialize(MinCharacterPasswordValidationAnnotation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Matcher matcher = PASSWORD_REGEX.matcher(value);
        return matcher.matches();
    }
}
