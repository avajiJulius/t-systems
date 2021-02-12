package com.logiweb.avaji.validation;

import com.logiweb.avaji.dao.ValidatorDAO;
import com.logiweb.avaji.validation.annotation.TruckIDUnique;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TruckIDUniqueValidator implements ConstraintValidator<TruckIDUnique, String> {

    private final ValidatorDAO validatorDAO;

    @Autowired
    public TruckIDUniqueValidator(ValidatorDAO validatorDAO) {
        this.validatorDAO = validatorDAO;
    }

    @Override
    public void initialize(TruckIDUnique constraintAnnotation) {
    }

    @Override
    public boolean isValid(String truckId, ConstraintValidatorContext constraintValidatorContext) {
        long result = validatorDAO.checkIdUnique(truckId);
        return (result == 0);
    }
}
