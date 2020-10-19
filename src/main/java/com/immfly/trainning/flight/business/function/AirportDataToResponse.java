package com.immfly.trainning.flight.business.function;

import com.immfly.trainning.flight.business.domain.redis.Airport;
import com.immfly.trainning.flight.business.domain.rest.response.AirportResponse;
import org.springframework.stereotype.Component;

@Component
public class AirportDataToResponse {

    public AirportResponse wrapData(final Airport airport) {
        AirportResponse response = new AirportResponse();

        response.setId(airport.getId());
        response.setAlternateCode(airport.getAlternateCode());
        response.setCity(airport.getCity());
        response.setName(airport.getName());

        return response;
    }
}
