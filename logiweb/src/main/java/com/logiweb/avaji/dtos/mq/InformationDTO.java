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


    public static class Builder {
        private InformationDTO newInformation;

        public Builder withOrderInfo(OrderInfo orderInfo) {
            newInformation.orderInfo = orderInfo;
            return this;
        }

        public Builder withTruckInfo(TruckInfo truckInfo) {
            newInformation.truckInfo = truckInfo;
            return this;
        }

        public Builder withDriverInfo(DriverInfo driverInfo) {
            newInformation.driverInfo = driverInfo;
            return this;
        }

        public InformationDTO build() {
            return newInformation;
        }
    }
}
