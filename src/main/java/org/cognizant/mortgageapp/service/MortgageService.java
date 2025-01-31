package org.cognizant.mortgageapp.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
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
        mortgageRates.sort(Comparator.comparingInt(MortgageRate::getMaturityPeriod));
    }

    public List<MortgageRate> getMortgageRates() {
        return mortgageRates;
    }

    /**
     * Checks the feasibility of a mortgage based on the provided request.
     *
     * @param request the mortgage check request containing income, maturity period, loan value, and home value
     * @return a response indicating whether the mortgage is feasible and the monthly cost if feasible
     * @throws IllegalArgumentException if the maturity period is invalid
     */
    public MortgageCheckResponse checkMortgage(MortgageCheckRequest request) {
        log.info("Checking mortgage feasibility for request: {}", request);

        MortgageCheckResponse response = new MortgageCheckResponse();

        double maxLoanValue = request.getIncome() * 4;
        boolean isLoanWithinIncome = request.getLoanValue() <= maxLoanValue;
        boolean isLoanWithinHomeValue = request.getLoanValue() <= request.getHomeValue();

        boolean feasible = isLoanWithinIncome && isLoanWithinHomeValue;

        response.setFeasible(feasible);
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

        if (feasible) {
            double interestRate = mortgageRates.stream()
                    .filter(rate -> rate.getMaturityPeriod() == request.getMaturityPeriod())
                    .map(MortgageRate::getInterestRate)
                    .findFirst()
                    .orElseThrow(() -> {
                        log.error("Invalid maturity period: {}", request.getMaturityPeriod());
                        return new IllegalArgumentException("Invalid maturity period");
                    });

            double monthlyCosts = getMonthlyCosts(request, interestRate);
            response.setMonthlyCost(decimalFormat.format(monthlyCosts));

            log.info("Mortgage feasible with monthly cost: {}", decimalFormat.format(monthlyCosts));
        } else {
            response.setMonthlyCost(decimalFormat.format(0));
            log.info("Mortgage not feasible");
        }
        return response;
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
        int numberOfPayments = request.getMaturityPeriod() * 12;   // Total number of monthly payments

        // formula: (1+monthlyRate)^numberOfPayments or (1+r)^n
        double mathPowFactor =Math.pow(1+monthlyRate, numberOfPayments);

        //MonthlyCosts = Principal(loanValue) * monthlyRate * [(1+r)^n]/ [(1+r)^n - 1]
        return request.getLoanValue() * (monthlyRate * mathPowFactor)/(mathPowFactor -1);
    }

}
