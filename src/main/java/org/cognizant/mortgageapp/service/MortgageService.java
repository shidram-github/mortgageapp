package org.cognizant.mortgageapp.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.cognizant.mortgageapp.model.MortgageCheckRequest;
import org.cognizant.mortgageapp.model.MortgageCheckResponse;
import org.cognizant.mortgageapp.model.MortgageRate;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class MortgageService {
    private final List<MortgageRate> mortgageRates = new ArrayList<>();
    DecimalFormat decimalFormat = new DecimalFormat("#.##");

    /**
     * Initializes the mortgage rates with predefined values and sorts them by maturity period.     *
     */
    @PostConstruct
    public void init() {
        log.info("Initializing mortgage rates");
        mortgageRates.addAll(List.of(
                new MortgageRate(10, 2.5, LocalDateTime.now()),
                new MortgageRate(15, 3.0, LocalDateTime.now()),
                new MortgageRate(20, 3.5, LocalDateTime.now()),
                new MortgageRate(30, 4.0, LocalDateTime.now())
        ));
        mortgageRates.sort(Comparator.comparingInt(MortgageRate::maturityPeriod));
    }

    public List<MortgageRate> getMortgageRates() {
        return mortgageRates;
    }

    /**
     * Checks the feasibility of a mortgage based on the provided request.
     *
     * @param mortgageCheckRequest the mortgage check request containing income, maturity period, loan value, and home value
     * @return a response indicating whether the mortgage is feasible and the monthly cost if feasible
     * @throws IllegalArgumentException if the maturity period is invalid
     */
    public MortgageCheckResponse checkMortgage(MortgageCheckRequest mortgageCheckRequest) {
        log.info("Checking mortgage feasibility for request: {}", mortgageCheckRequest);

        double maxLoanValue = mortgageCheckRequest.income() * 4;
        boolean isLoanWithinIncome = mortgageCheckRequest.loanValue() <= maxLoanValue;
        boolean isLoanWithinHomeValue = mortgageCheckRequest.loanValue() <= mortgageCheckRequest.homeValue();

        boolean feasible = isLoanWithinIncome && isLoanWithinHomeValue;
        double monthlyCosts=0;
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

        if (feasible) {
            double interestRate = mortgageRates.stream()
                    .filter(rate -> rate.maturityPeriod() == mortgageCheckRequest.maturityPeriod())
                    .map(MortgageRate::interestRate)
                    .findFirst()
                    .orElseThrow(() -> {
                        log.error("Invalid maturity period: {}", mortgageCheckRequest.maturityPeriod());
                        return new IllegalArgumentException("Invalid maturity period");
                    });

            monthlyCosts = getMonthlyCosts(mortgageCheckRequest, interestRate);
            log.info("Mortgage feasible with monthly cost: {}", decimalFormat.format(monthlyCosts));
        } else {
            log.info("Mortgage not feasible");
        }
        return new MortgageCheckResponse(feasible, decimalFormat.format(monthlyCosts));
    }

    /**
     * Calculates the monthly mortgage costs based on the provided request and interest rate.
     *
     * @param request the mortgage check request containing loan value and maturity period
     * @param interestRate the interest rate for the mortgage
     * @return the calculated monthly mortgage cost
     */
    private double getMonthlyCosts(MortgageCheckRequest request, double interestRate) {
        double monthlyRate = (interestRate / 100) / 12;    // Monthly interest rate
        int numberOfPayments = request.maturityPeriod() * 12;   // Total number of monthly payments

        // formula: (1+monthlyRate)^numberOfPayments or (1+r)^n
        double mathPowFactor =Math.pow(1+monthlyRate, numberOfPayments);

        //MonthlyCosts = Principal(loanValue) * monthlyRate * [(1+r)^n]/ [(1+r)^n - 1]
        return request.loanValue() * (monthlyRate * mathPowFactor)/(mathPowFactor -1);
    }

}
