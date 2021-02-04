package com.logiweb.avaji.entity.model.mq;

import lombok.Data;

@Data
public class DriverInfo {
    private long totalNumber;
    private long availableNumber;
    private long unavailableNumber;

    public static class Builder {
        private DriverInfo newDriverInfo;

        public Builder() {
            newDriverInfo = new DriverInfo();
        }

        public Builder withTotalNumber(long totalNumber) {
            newDriverInfo.totalNumber = totalNumber;
            return this;
        }

        public Builder withAvailableNumber(long availableNumber) {
            newDriverInfo.availableNumber = availableNumber;
            return this;
        }

        public Builder withUnavailableNumber(long unavailableNumber) {
            newDriverInfo.unavailableNumber = unavailableNumber;
            return this;
        }

        public DriverInfo build() {
            return newDriverInfo;
        }
    }
}
