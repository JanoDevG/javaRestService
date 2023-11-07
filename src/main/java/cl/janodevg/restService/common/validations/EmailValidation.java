package cl.janodevg.restService.common.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidation implements ConstraintValidator<EmailValidationAnnotation, String> {

    private static final Pattern EMAIL_REGEX = Pattern.compile(
            "^([a-zA-Z0-9_\\-]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$"
    );

    @Override
    public void initialize(EmailValidationAnnotation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Matcher matcher = EMAIL_REGEX.matcher(value);
        return matcher.matches();
    }
}
