package com.immfly.trainning.flight.business.redis.repository;

import com.immfly.trainning.flight.business.domain.redis.Flight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlightRepository extends CrudRepository<Flight, String> {

    /**
     *  Find by Tail and Flight number.
     *
     * @param tailNumber tail number.
     * @param number flight number.
     * @return a flight.
     */
    Optional<Flight> findByNumberAndTailNumber(Integer number, String tailNumber);

    Optional<Flight> findByNumber(Integer number);

    Optional<Flight> findByTailNumber(String tailNumber);
}
