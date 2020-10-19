package com.immfly.trainning.flight.business.domain.rest.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class FlightResponse implements Serializable {

    /** ident. */
    private String ident;
    /** faFlightId. */
    private String faFlightID;
    /** airline. */
    private String airline;
    /** airlineIata. */
    private String airline_iata;
    /** flightNumber. */
    private Integer flightnumber;
    /** tailnumber. */
    private String tailnumber;
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
    private AirportResponse origin;
    /** destination. */
    private AirportResponse destination;
}
