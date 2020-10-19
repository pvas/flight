package com.immfly.trainning.flight.business.function;

import com.immfly.trainning.flight.business.domain.redis.Flight;
import com.immfly.trainning.flight.business.domain.rest.response.FlightResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlightDataToResponse {

    @Autowired
    private AirportDataToResponse airportDataToResponse;

    public FlightResponse wrapData(final Flight flight) {
        FlightResponse response = new FlightResponse();
        response.setAirline(flight.getAirline());
        response.setAirline_iata(flight.getAirlineIata());
        response.setBlocked(flight.getBlocked());
        response.setCancelled(flight.getCancelled());
        response.setCodeshares(flight.getCodeshares());
        response.setDiverted(flight.getDiverted());
        response.setFaFlightID(flight.getFaFlightId());
        response.setFlightnumber(flight.getNumber());
        response.setIdent(flight.getId());
        response.setTailnumber(flight.getTailNumber());
        response.setType(flight.getType());

        response.setDestination(airportDataToResponse.wrapData(flight.getDestination()));
        response.setOrigin(airportDataToResponse.wrapData(flight.getOrigin()));

        return response;
    }
}
