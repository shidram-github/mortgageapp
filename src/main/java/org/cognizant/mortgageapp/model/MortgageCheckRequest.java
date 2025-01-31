package org.cognizant.mortgageapp.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MortgageCheckRequest {
    @NotNull
    @Min(value = 1, message = "Income should be greater than 0")
    private double income;

    @NotNull
    @Min(value = 10, message = "Maturity period should be greater than or equal to minimum range")
    @Max(value = 30, message = "Maturity period should not be greater than maximum range")
    private int maturityPeriod;

    @NotNull
    @Min(value = 1, message = "Loan value should be greater than 0")
    private double loanValue;

    @NotNull
    @Min(value = 1, message = "Home value should be greater than 0")
    private double homeValue;
}
