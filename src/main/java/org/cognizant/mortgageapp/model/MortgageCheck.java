package org.cognizant.mortgageapp.model;

import lombok.Data;

@Data
public class MortgageCheck {
    private double income;
    private int maturityPeriod;
    private double loanValue;
    private double homeValue;
}
