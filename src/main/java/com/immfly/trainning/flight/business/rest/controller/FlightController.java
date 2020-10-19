package com.immfly.trainning.flight.business.rest.controller;


import com.immfly.trainning.flight.business.service.FlightService;
import com.immfly.trainning.flight.business.domain.rest.request.FlightInformationRequest;
import com.immfly.trainning.flight.business.domain.rest.response.FlightResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class FlightController extends VersionController {

    @Autowired
    private FlightService service;

    @RequestMapping(value = "/flight-information/{tailNumber}/{flightNumber}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public FlightResponse getFlightInfoByTailAndFlightNumber(@PathVariable String tailNumber,
                                                             @PathVariable Integer flightNumber) {
        FlightResponse response = null;

        if (tailNumber != null && flightNumber != null) {
            FlightInformationRequest request = new FlightInformationRequest();
            request.setTailNumber(tailNumber);
            request.setFlightNumber(flightNumber);
            response = service.getFlightInformation(request);
        }

        return response;
    }
}
