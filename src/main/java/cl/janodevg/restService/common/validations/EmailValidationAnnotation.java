package cl.janodevg.restService.common.validations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailValidationAnnotation {

    String message() default "In the Request, the email isn't valid value.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
