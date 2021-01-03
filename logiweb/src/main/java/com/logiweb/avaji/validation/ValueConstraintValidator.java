package com.logiweb.avaji.validation;

import com.logiweb.avaji.annotation.ValueConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValueConstraintValidator implements ConstraintValidator<ValueConstraint, Number> {

    @Override
    public boolean isValid(Number number, ConstraintValidatorContext constraintValidatorContext) {
        return number.doubleValue() > 0.0;
    }
}
