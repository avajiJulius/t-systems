package com.logiweb.avaji.crud.workdetails.service.api;


import com.logiweb.avaji.crud.workdetails.dto.ChangeCityDTO;
import com.logiweb.avaji.crud.workdetails.dto.ShiftDetailsDto;
import com.logiweb.avaji.crud.workdetails.dto.WorkDetailsDTO;
import com.logiweb.avaji.exceptions.DriverStatusNotFoundException;
import com.logiweb.avaji.exceptions.ShiftValidationException;

import java.util.List;

public interface WorkDetailsService {
    WorkDetailsDTO readWorkDetailsById(long userId);
    void updateOrderByCargoStatus(long orderId, List<Long> cargoIds);
    void updateShiftDetails(long id, ShiftDetailsDto shiftDetails) throws ShiftValidationException, DriverStatusNotFoundException;

    List<Long> readCoDriversIds(String id);

    void updateDriverCity(long driverId, long cityCode);
}
