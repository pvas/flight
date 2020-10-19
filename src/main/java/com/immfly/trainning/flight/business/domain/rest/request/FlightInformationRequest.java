package com.immfly.trainning.flight.business.domain.rest.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class FlightInformationRequest implements Serializable {
    private String tailNumber;
    private Integer flightNumber;
}
