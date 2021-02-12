package com.logiweb.avaji.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CargoStatus {
    PREPARED("prepared"),
    SHIPPED("shipped"),
    DELIVERED("delivered");

    private final String status;
}
