package com.message.service.domain.validation;

import javax.validation.*;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ValidPassword {

    String message() default "Неверный пароль";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
