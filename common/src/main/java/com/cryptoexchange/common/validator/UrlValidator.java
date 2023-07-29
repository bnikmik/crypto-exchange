package com.cryptoexchange.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.net.URL;

public class UrlValidator implements ConstraintValidator<ValidURL, URL> {

    @Override
    public void initialize(ValidURL constraintAnnotation) {
    }

    @Override
    public boolean isValid(URL value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            value.toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
