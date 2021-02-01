package com.logiweb.avaji.service.api.validator;

public interface UniqueValidatorService {
    void validateEmailUnique(String email);
    void validateTruckIdUnique(String id);
}
