package com.immfly.trainning.flight.business.redis.repository;

import com.immfly.trainning.flight.business.domain.redis.Flight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends CrudRepository<Flight, String> {
}
