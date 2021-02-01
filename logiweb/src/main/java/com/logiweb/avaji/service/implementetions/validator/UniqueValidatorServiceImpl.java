package com.logiweb.avaji.service.implementetions.validator;

import com.logiweb.avaji.dao.ValidatorDAO;
import com.logiweb.avaji.exception.UniqueValidationException;
import com.logiweb.avaji.service.api.validator.UniqueValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniqueValidatorServiceImpl implements UniqueValidatorService {

    private final ValidatorDAO validatorDAO;

    @Autowired
    public UniqueValidatorServiceImpl(ValidatorDAO validatorDAO) {
        this.validatorDAO = validatorDAO;
    }

    public void validateEmailUnique(String email) {
        long result = validatorDAO.checkEmailUnique(email);
        if(result != 0) {
            throw new UniqueValidationException("Email already in use", "drivers");
        }
    }

    public void validateTruckIdUnique(String id) {
        long result = validatorDAO.checkIdUnique(id);
        if(result != 0) {
            throw new UniqueValidationException("Truck with this ID already exist", "trucks");
        }
    }
}
