package com.immfly.trainning.flight.service;

import com.immfly.trainning.flight.business.domain.redis.Flight;
import com.immfly.trainning.flight.business.domain.rest.request.FlightInformationRequest;
import com.immfly.trainning.flight.business.redis.repository.FlightRepository;
import com.immfly.trainning.flight.business.service.impl.FlightServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FlightServiceTest {

	@InjectMocks private FlightServiceImpl flightService;
	
	@Mock
	FlightRepository flightRepository;

	private FlightInformationRequest flightInformationRequest;

	@BeforeEach
	public void initTestData() {
		this.flightInformationRequest = new FlightInformationRequest();
	}
	
	
	@Test
	public void isFlightServiceInjectedTest() {
		Assert.assertNotNull(flightService);
	}
	
	@Test
	public void getFlightInformationWithValidRequestTest() {
		flightInformationRequest.setTailNumber("EC-MYT");
		flightInformationRequest.setFlightNumber(653);
	
		flightService.getFlightInformation(flightInformationRequest);
		verify(flightRepository, atLeastOnce()).findAll();
	}
	
	@Test
	public void getFlightInformationWithNullRequestTest() {
		flightInformationRequest = null;
		assertThrows(NullPointerException.class, () ->
			flightService.getFlightInformation(flightInformationRequest)
	    );
		verify(flightRepository, never()).findAll();
	}
	
	
	@Test
	public void getFlightInformationWithInvalidRequestTest() {
		flightInformationRequest = new FlightInformationRequest();
		flightInformationRequest.setTailNumber("PT-BHZ");
		flightInformationRequest.setFlightNumber(111);
	
		flightService.getFlightInformation(flightInformationRequest);

		verify(flightRepository, never()).save(any(Flight.class));
	}
	
}
