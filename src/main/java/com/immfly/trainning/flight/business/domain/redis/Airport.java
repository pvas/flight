package com.immfly.trainning.flight.business.domain.redis;

import lombok.Data;

import java.io.Serializable;

@Data
public class Airport implements Serializable {

    /** code. */
    private String id;
    /** alternative code. */
    private String alternateCode;
    /** city. */
    private String city;
    /** name. */
    private String name;
}
