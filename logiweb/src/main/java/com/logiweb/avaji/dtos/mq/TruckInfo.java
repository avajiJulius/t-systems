package com.logiweb.avaji.dtos.mq;

import lombok.Data;

import java.io.Serializable;

@Data
public class TruckInfo implements Serializable {
    private int totalNumber;
    private long availableNumber;
    private long inUseNumber;
    private long faultyNumber;

    public static class Builder {
        private TruckInfo newTruckInfo;

        public Builder() {
            newTruckInfo = new TruckInfo();
        }

        public Builder withTotal(int totalNumber) {
            newTruckInfo.totalNumber = totalNumber;
            return this;
        }

        public Builder withAvailable(long availableNumber) {
            newTruckInfo.availableNumber = availableNumber;
            return this;
        }

        public Builder withInUse(long inUseNumber) {
            newTruckInfo.inUseNumber = inUseNumber;
            return this;
        }


        public Builder withFaulty(long faultyNumber) {
            newTruckInfo.faultyNumber = faultyNumber;
            return this;
        }

        public TruckInfo build() {
            return newTruckInfo;
        }
    }
}
