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

import javax.annotation.PostConstruct;
import java.util.Optional;

@Slf4j
@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightDataToResponse flightDataToResponse;

    @PostConstruct
    private void insertInit() {
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

    /**
     * Method in charge to retrieve a flight information by request.
     *
     * @param request a Flight Information Request.
     * @return a Flight Response.
     */
    public FlightResponse getFlightInformation(FlightInformationRequest request) {
        FlightResponse response = new FlightResponse();
        Flight flight;
//        flight = flightRepository.findByTailNumberAndNumber(request.getTailNumber(), request.getFlightNumber());
        Optional<Flight> flightOptional;// = flightRepository.findById("IBB653");
        flightOptional = flightRepository.findByNumber(request.getFlightNumber());
        flightOptional = flightRepository.findByTailNumber(request.getTailNumber());
        if (!flightOptional.isPresent()) {
            //find in external webservice
        } else {
            flight = flightOptional.get();
            log.info("Flight id: {}", flight.getId());
            response = flightDataToResponse.wrapData(flight);
        }
        return response;
    }

}
