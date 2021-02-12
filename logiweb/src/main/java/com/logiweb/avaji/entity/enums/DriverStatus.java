package com.logiweb.avaji.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DriverStatus {
    REST("rest"),
    DRIVING("driving"),
    SECOND_DRIVER("second driver"),
    LOAD_UNLOAD_WORK("load/unload work");

    private final String status;
}
