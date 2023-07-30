package com.cryptoexchange.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.net.URL;

public class UrlValidator implements ConstraintValidator<ValidURL, URL> {

    private static final String URL_REGEX = "^((https?|ftp)://)?([\\w_-]+(\\.[\\w_-]+)+([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-]))?$";

    @Override
    public void initialize(ValidURL constraintAnnotation) {
    }

    @Override
    public boolean isValid(URL value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            String urlString = value.toExternalForm();
            return urlString.matches(URL_REGEX);
        } catch (Exception e) {
            return false;
        }
    }
}
