package com.logiweb.avaji.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoadDTO {
    private long cityACode;
    private long cityBCode;
    private double distance;
}
