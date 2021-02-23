package com.logiweb.avaji.service.api.validator;


/**
 * This service used by Validators witch used in validation annotations
 *
 */
public interface UniqueValidatorService {
    /**
     * Validate Unique of email address
     *
     * @param email
     */
    void validateEmailUnique(String email);

    /**
     * Validate Unique of truckId
     *
     * @param id of truck
     */
    void validateTruckIdUnique(String id);
}
