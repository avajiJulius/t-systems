package com.logiweb.avaji.dtos.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoDriverDTO {
    private long id;
    private String firstName;
    private String lastName;
}
