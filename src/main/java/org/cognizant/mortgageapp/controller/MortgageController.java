package org.cognizant.mortgageapp.controller;

import jakarta.validation.Valid;
import org.cognizant.mortgageapp.exception.ResourceNotFoundException;
import org.cognizant.mortgageapp.model.MortgageCheckRequest;
import org.cognizant.mortgageapp.model.MortgageCheckResponse;
import org.cognizant.mortgageapp.model.MortgageRate;
import org.cognizant.mortgageapp.service.MortgageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MortgageController {
    @Autowired
    private MortgageService mortgageService;

    @GetMapping("/interest-rates")
    public List<MortgageRate> getInterestRates() {
        return Optional.ofNullable(mortgageService.getMortgageRates())
                .filter(rates -> !rates.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("No mortgage rates found"));
    }

    @PostMapping("/mortgage-check")
    public MortgageCheckResponse checkMortgage(@RequestBody @Validated MortgageCheckRequest request) {
        return mortgageService.checkMortgage(request);
    }
}
