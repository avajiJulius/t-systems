package com.logiweb.avaji.dtos.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformationDTO implements Serializable {

    private OrderInfo orderInfo;
    private TruckInfo truckInfo;
    private DriverInfo driverInfo;

}
