package org.cognizant.mortgageapp.model;

import java.time.LocalDateTime;

public record MortgageRate(int maturityPeriod, double interestRate, LocalDateTime lastUpdate) {
}
