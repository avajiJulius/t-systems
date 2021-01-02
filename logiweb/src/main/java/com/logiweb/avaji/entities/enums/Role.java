package com.logiweb.avaji.entities.enums;


import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Role {
    DRIVER(Stream.of(Permission.DRIVER_READ, Permission.DRIVER_WRITE).collect(Collectors.toSet())),
    EMPLOYEE(Stream.of(Permission.EMPLOYEE_READ, Permission.EMPLOYEE_WRITE).collect(Collectors.toSet()));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }


}
