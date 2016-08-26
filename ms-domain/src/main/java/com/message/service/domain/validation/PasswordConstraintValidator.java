package com.message.service.domain.validation;

import com.google.common.base.Joiner;
import org.passay.*;

import javax.validation.*;
import java.util.Arrays;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    public void initialize(final ValidPassword validPassword) {
    }

    public boolean isValid(final String s, final ConstraintValidatorContext context) {
        final PasswordValidator validator = new PasswordValidator(
                Arrays.asList(
                        new LengthRule(8, 255),
                        new UppercaseCharacterRule(1),
                        new WhitespaceRule()
                )
        );
        final RuleResult result = validator.validate(new PasswordData(s));
        if (result.isValid()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(Joiner.on("\n").join(validator.getMessages(result))).addConstraintViolation();
        return false;
    }

}
