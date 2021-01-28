package com.logiweb.avaji.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permission {
    EMPLOYEE_READ("employee:read"),
    DRIVER_READ("driver:read"),
    EMPLOYEE_WRITE("employee:write"),
    DRIVER_WRITE("driver:write");

    private final String permission;
}
