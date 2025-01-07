package org.cognizant.mortgageapp.service;

import org.cognizant.mortgageapp.model.MortgageCheckRequest;
import org.cognizant.mortgageapp.model.MortgageCheckResponse;
import org.cognizant.mortgageapp.model.MortgageRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MortgageServiceTest {

    private MortgageService mortgageService;

    @BeforeEach
    public void setUp() {
        mortgageService = new MortgageService();
        mortgageService.init();
    }

    @Test
    public void testGetMortgageRates() {
        List<MortgageRate> rates = mortgageService.getMortgageRates();
        assertNotNull(rates);
        assertFalse(rates.isEmpty());
        assertEquals(4, rates.size());
    }

    @Test
    public void testCheckMortgageFeasible() {
        MortgageCheckRequest request = new MortgageCheckRequest(50000, 30, 200000, 250000);
        MortgageCheckResponse response = mortgageService.checkMortgage(request);
        assertTrue(response.isFeasible());
        assertNotEquals("0.00",response.getMonthlyCost());
    }

    @Test
    public void testCheckMortgageNotFeasible() {
        MortgageCheckRequest request = new MortgageCheckRequest(50000, 30, 300000, 250000);
        MortgageCheckResponse response = mortgageService.checkMortgage(request);
        assertFalse(response.isFeasible());
        assertEquals("0", response.getMonthlyCost());
    }

    @Test
    public void testCheckMortgageInvalidMaturityPeriod() {
        MortgageCheckRequest request = new MortgageCheckRequest(50000, 25, 200000, 250000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mortgageService.checkMortgage(request);
        });
        assertEquals("Invalid maturity period", exception.getMessage());
    }
}