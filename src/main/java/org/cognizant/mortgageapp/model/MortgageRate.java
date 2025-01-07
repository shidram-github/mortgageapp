package org.cognizant.mortgageapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MortgageRate {
    private int maturityPeriod;
    private double interestRate;
    private LocalDateTime lastUpdate;
}
