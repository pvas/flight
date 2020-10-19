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
    private String id;
    /** faFlightId. */
    private String faFlightId;
    /** airline. */
    private String airline;
    /** airlineIata. */
    private String airlineIata;
    /** flightNumber. */
    @Indexed
    private Integer number;
    /** tailnumber. */
    @Indexed
    private String tailNumber;
    /** type. */
    private String type;
    /** codeshares. */
    private String codeshares;
    /** blocked. */
    private Boolean blocked;
    /** diverted. */
    private Boolean diverted;
    /** cancelled. */
    private Boolean cancelled;
    /** origin. */
    private Airport origin;
    /** destination. */
    private Airport destination;

    public Boolean compareByTailAndFlightNumber(String tailNumber, Integer flightNumber) {
        return this.getTailNumber().equals(tailNumber)
                && this.getNumber().equals(flightNumber);
    }
}
