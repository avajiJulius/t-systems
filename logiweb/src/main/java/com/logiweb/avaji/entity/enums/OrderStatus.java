package com.logiweb.avaji.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    COMPLETED("completed"),
    UNCOMPLETED("uncompleted"),
    CANCELLED("cancelled");

    private final String status;
}
