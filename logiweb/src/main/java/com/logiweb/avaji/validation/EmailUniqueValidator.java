package com.logiweb.avaji.validation;


import com.logiweb.avaji.dao.ValidatorDAO;
import com.logiweb.avaji.validation.annotation.EmailUnique;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, String> {

    private final ValidatorDAO validatorDAO;

    @Autowired
    public EmailUniqueValidator(ValidatorDAO validatorDAO) {
        this.validatorDAO = validatorDAO;
    }

    @Override
    public void initialize(EmailUnique constraintAnnotation) {

    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        long result = validatorDAO.checkEmailUnique(email);
        return (result == 0);
    }
}
