package com.immfly.trainning.flight.business.rest.controller;


import com.immfly.trainning.flight.business.domain.rest.response.FlightResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/flight-information")
public class FlightController extends VersionController {

    @RequestMapping(value = "/{tailNumber}/{flightNumber}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public FlightResponse getFlightInfoByTailAndFlightNumber(@PathVariable String tailNumber,
                                                             @PathVariable String flightNumber) {
        return null;
    }
}
