package org.cognizant.mortgageapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cognizant.mortgageapp.model.MortgageCheckRequest;
import org.cognizant.mortgageapp.model.MortgageCheckResponse;
import org.cognizant.mortgageapp.model.MortgageRate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MortgageControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetInterestRates() {
        ResponseEntity<MortgageRate[]> response = restTemplate.getForEntity("/api/interest-rates", MortgageRate[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testCheckMortgage() {
        MortgageCheckRequest request = new MortgageCheckRequest(50000, 30, 200000, 250000);
        MortgageCheckResponse expectedResponse = new MortgageCheckResponse(true, "954.83");

        ResponseEntity<MortgageCheckResponse> response = restTemplate.postForEntity("/api/mortgage-check", request, MortgageCheckResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
        assertTrue(response.getBody().isFeasible());
    }
}