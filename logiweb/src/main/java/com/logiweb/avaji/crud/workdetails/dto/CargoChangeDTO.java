package com.logiweb.avaji.crud.workdetails.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoChangeDTO {
    private List<Long> ids = new ArrayList<>();
}
