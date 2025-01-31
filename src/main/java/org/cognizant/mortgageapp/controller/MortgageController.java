package org.cognizant.mortgageapp.controller;

import jakarta.validation.Valid;
import org.cognizant.mortgageapp.model.MortgageCheckRequest;
import org.cognizant.mortgageapp.model.MortgageCheckResponse;
import org.cognizant.mortgageapp.model.MortgageRate;
import org.cognizant.mortgageapp.service.MortgageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MortgageController {

    private final MortgageService mortgageService;

    public MortgageController(MortgageService mortgageService) {
        this.mortgageService = mortgageService;
    }

    @GetMapping("/interest-rates")
    public List<MortgageRate> getInterestRates() {
        return mortgageService.getMortgageRates();
    }

    @PostMapping("/mortgage-check")
    public MortgageCheckResponse checkMortgage(@RequestBody @Valid MortgageCheckRequest request) {
        return mortgageService.checkMortgage(request);
    }
}
