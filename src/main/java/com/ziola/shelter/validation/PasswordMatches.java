package com.ziola.shelter.validation;

import com.ziola.shelter.validation.PasswordMatchesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {
    String message() default "Hasło jest nieprawidłowe";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
