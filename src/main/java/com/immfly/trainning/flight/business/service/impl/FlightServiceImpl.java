package com.immfly.trainning.flight.business.service.impl;

import com.immfly.trainning.flight.business.config.redis.RedisConfiguration;
import com.immfly.trainning.flight.business.domain.redis.Airport;
import com.immfly.trainning.flight.business.service.FlightService;
import com.immfly.trainning.flight.business.domain.redis.Flight;
import com.immfly.trainning.flight.business.domain.rest.request.FlightInformationRequest;
import com.immfly.trainning.flight.business.domain.rest.response.FlightResponse;
import com.immfly.trainning.flight.business.function.FlightDataToResponse;
import com.immfly.trainning.flight.business.redis.repository.FlightRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightDataToResponse flightDataToResponse;

    /**
     * Method helper that return an initial Flight object to persist in cache.
     *
     * @return
     */
    private Flight getInitialFlight() {
        Flight flight = new Flight();
        flight.setId("IBB653");
        flight.setFaFlightId("IBB653-1581399936-airline-0136");
        flight.setAirline("IBB");
        flight.setAirlineIata("NT");
        flight.setNumber(653);
        flight.setTailNumber("EC-MYT");
        flight.setType("Form_Airline");
        flight.setCodeshares("IBE123");
        flight.setBlocked(Boolean.FALSE);
        flight.setDiverted(Boolean.FALSE);
        flight.setCancelled(Boolean.FALSE);

        Airport origin = new Airport();
        origin.setId("GCXO");
        origin.setAlternateCode("TFN");
        origin.setCity("Tenerife");
        origin.setName("Tenerife North (Los Rodeos)");

        Airport destination = new Airport();
        destination.setId("GCGM");
        destination.setAlternateCode("GMZ");
        destination.setCity("La Gomera");
        destination.setName("La Gomera");

        flight.setOrigin(origin);
        flight.setDestination(destination);

        return flight;
    }

    /**
     * Method in charge to retrieve a flight information by request.
     *
     * @param request a Flight Information Request.
     * @return a Flight Response.
     */
    public FlightResponse getFlightInformation(FlightInformationRequest request) {
        FlightResponse response = new FlightResponse();
        Iterable<Flight> flightsIt = flightRepository.findAll();
        List<Flight> flightList = new ArrayList<>();
        flightsIt.forEach(flightList::add);

        Optional<Flight> flightOptional = flightList.stream().filter(x
                -> x.compareByTailAndFlightNumber(request.getTailNumber(), request.getFlightNumber())).findAny();

        if (!flightOptional.isPresent()) {
            //find in external service
            updateCache(getInitialFlight());
        } else {
            log.info("Retrieving info for Flight id: {}", flightOptional.get().getId());
            response = flightDataToResponse.wrapData(flightOptional.get());
        }
        return response;
    }

    /**
     * Method in charge to save in Redis a new Flight.
     *
     * @param flight flight to be saved.
     */
    private void updateCache(Flight flight) {
        try (Jedis jedis = RedisConfiguration.jedisPool.getResource()) {
            jedis.getClient().setTimeoutInfinite();
            Optional<Flight> response = flightRepository.findById(flight.getId());
            if (!response.isPresent()) {
                flightRepository.save(flight);
            }
        } finally {
            RedisConfiguration.jedisPool.destroy();
        }
    }

}
