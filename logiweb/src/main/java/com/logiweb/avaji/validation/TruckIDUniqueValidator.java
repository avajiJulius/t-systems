package com.logiweb.avaji.validation;

import com.logiweb.avaji.dao.ValidatorDAO;
import com.logiweb.avaji.validation.annotation.TruckIDUnique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class TruckIDUniqueValidator implements ConstraintValidator<TruckIDUnique, String> {

    private final ValidatorDAO validatorDAO;

    @Autowired
    public TruckIDUniqueValidator(ValidatorDAO validatorDAO) {
        this.validatorDAO = validatorDAO;
    }

    @Override
    public boolean isValid(String truckId, ConstraintValidatorContext constraintValidatorContext) {
        return validatorDAO.checkIdUnique(truckId) == 0;
    }
}
