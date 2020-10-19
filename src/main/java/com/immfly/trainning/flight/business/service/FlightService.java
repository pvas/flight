package com.immfly.trainning.flight.business.service;

import com.immfly.trainning.flight.business.domain.rest.request.FlightInformationRequest;
import com.immfly.trainning.flight.business.domain.rest.response.FlightResponse;

public interface FlightService {

    FlightResponse getFlightInformation(FlightInformationRequest request);

}
