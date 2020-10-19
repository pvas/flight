package com.immfly.trainning.flight.business.domain.rest.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class FlightResponse implements Serializable {

    /** ident. */
    private String ident;//": "IBB653",
    /** faFlightId. */
    private String faFlightID;//": "IBB653-1581399936-airline-0136",
    /** airline. */
    private String airline;//": "IBB",
    /** airlineIata. */
    private String airline_iata;//": "NT",
    /** flightNumber. */
    private Integer flightnumber;//": "653",
    /** tailnumber. */
    private String tailnumber;//": "EC-MYT",
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
    private AirportResponse origin;
    /** destination. */
    private AirportResponse destination;
}
