package com.logiweb.avaji.orderdetails.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vertex {
    private long cityCode;
    private Set<Vertex> connected = new HashSet<>();

    public Vertex(long cityCode) {
        this.cityCode = cityCode;
    }
}
