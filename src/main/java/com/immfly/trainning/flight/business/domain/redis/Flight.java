package com.immfly.trainning.flight.business.domain.redis;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Data
@RedisHash("Flight")
public class Flight implements Serializable {

    /** ident. */
    @Indexed
    private String id;//": "IBB653",
    /** faFlightId. */
    private String faFlightId;//": "IBB653-1581399936-airline-0136",
    /** airline. */
    private String airline;//": "IBB",
    /** airlineIata. */
    private String airlineIata;//": "NT",
    /** flightNumber. */
    @Indexed
    private Integer number;//": "653",
    /** tailnumber. */
    @Indexed
    private String tailNumber;//": "EC-MYT",
    /** type. */
    private String type;//": "Form_Airline",
    /** codeshares. */
    private String codeshares;//": "IBE123",
    /** blocked. */
    private Boolean blocked;//": false,
    /** diverted. */
    private Boolean diverted;//": false,
    /** cancelled. */
    private Boolean cancelled;//": false,
    /** origin. */
    private Airport origin;
    /** destination. */
    private Airport destination;
}
