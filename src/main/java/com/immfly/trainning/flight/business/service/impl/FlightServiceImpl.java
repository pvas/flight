package com.immfly.trainning.flight.business.service.impl;

import com.immfly.trainning.flight.config.redis.RedisConfiguration;
import com.immfly.trainning.flight.business.domain.redis.Airport;
import com.immfly.trainning.flight.business.service.FlightService;
import com.immfly.trainning.flight.business.domain.redis.Flight;
import com.immfly.trainning.flight.business.domain.rest.request.FlightInformationRequest;
import com.immfly.trainning.flight.business.domain.rest.response.FlightResponse;
import com.immfly.trainning.flight.business.function.FlightDataToResponseFunction;
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

    private final Integer flightNumberMock = 653;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightDataToResponseFunction flightDataToResponse;

    /**
     * Mock for test getInitialFlight
     * @param number
     * @return
     */
    public Optional<Flight> getInitialFlight(Integer number) {
        Flight response = null;
        if (number.equals(flightNumberMock)) {
            response = getInitialFlight();
        }
        return Optional.ofNullable(response);
    }

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
        FlightResponse response = null;
        Flight flight = null;
        if (request != null && request.getTailNumber() != null && request.getFlightNumber() != null) {
            Iterable<Flight> flightsIt = flightRepository.findAll();
            List<Flight> flightList = new ArrayList<>();
            flightsIt.forEach(flightList::add);

            Optional<Flight> flightOptional = flightList.stream().filter(x
                    -> x.compareByTailAndFlightNumber(request.getTailNumber(), request.getFlightNumber())).findAny();

            if (!flightOptional.isPresent()) {
                //todo find in external service
                log.info("Retrieving info for Flight number: {} and Tail number: {}",
                        request.getFlightNumber(), request.getTailNumber());
                Optional<Flight> initialCacheFlightOpt = getInitialFlight(request.getFlightNumber());
                if (initialCacheFlightOpt.isPresent() && !flightOptional.equals(Optional.empty())) {
                    log.info("Retrieved info for Flight ID: {}", initialCacheFlightOpt.get().getId());
                    flight = initialCacheFlightOpt.get();
                    if (flight.getNumber() != null) {
                        response = flightDataToResponse.wrapData(updateCache(flight));
                    }
                } else {
                    log.error("Could not retrieve info for Flight number: {} and Tail number: {}",
                            request.getFlightNumber(), request.getTailNumber());
                }
            } else if (!flightOptional.equals(Optional.empty())) {
                response = flightDataToResponse.wrapData(flightOptional.get());
            }
        } else {
            final String errorMessage = "Flight Information Request cannot be null.";
            log.error(errorMessage);
            throw new NullPointerException(errorMessage);
        }
        return response;
    }

    /**
     * Method in charge to save in Redis a new Flight.
     *
     * @param flight flight to be saved.
     * @return saved Flight.
     */
    private Flight updateCache(Flight flight) {
        Flight saved = null;
        try (Jedis jedis = RedisConfiguration.jedisPool.getResource()) {
            jedis.getClient().setTimeoutInfinite();
            Optional<Flight> response = flightRepository.findById(flight.getId());
            if (!response.isPresent()) {
                saved = flightRepository.save(flight);
                log.info("Saved Flight ID: {}", flight.getId());
            }
        } finally {
            RedisConfiguration.jedisPool.destroy();
        }
        return saved;
    }

}
