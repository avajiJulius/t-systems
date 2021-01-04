package com.logiweb.avaji.orderdetails.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddedDriversDto {
    private List<Long> ids = new ArrayList<>();

}
