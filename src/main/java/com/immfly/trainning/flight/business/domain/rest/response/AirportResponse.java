package com.immfly.trainning.flight.business.domain.rest.response;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class AirportResponse implements Serializable {

    /** code. */
    private String id;
    /** alternative code. */
    private String alternateCode;
    /** city. */
    private String city;
    /** name. */
    private String name;
}
