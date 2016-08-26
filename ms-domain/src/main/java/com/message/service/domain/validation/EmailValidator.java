package com.message.service.domain.validation;

import javax.validation.*;
import java.util.regex.*;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public void initialize(ValidEmail validEmail) {
    }

    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return validateEmail(s);
    }

    private boolean validateEmail(final String email) {
        final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        final Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
