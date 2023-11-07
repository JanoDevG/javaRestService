package cl.janodevg.restService.common.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MinCharacterPasswordValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MinCharacterPasswordValidationAnnotation {

    String message() default "In Request, the password does not meet the minimum safety condition.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
