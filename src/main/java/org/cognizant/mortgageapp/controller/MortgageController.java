package org.cognizant.mortgageapp.controller;

import jakarta.validation.Valid;
import org.cognizant.mortgageapp.model.MortgageCheckRequest;
import org.cognizant.mortgageapp.model.MortgageCheckResponse;
import org.cognizant.mortgageapp.model.MortgageRate;
import org.cognizant.mortgageapp.service.MortgageService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<MortgageRate>> getInterestRates() {
        return ResponseEntity.ok(mortgageService.getMortgageRates());
    }

    @PostMapping("/mortgage-check")
    public ResponseEntity<MortgageCheckResponse> checkMortgage(@RequestBody @Valid MortgageCheckRequest request) {
        System.out.println("Checking Mortgage:::"+Thread.currentThread().getName());
        return ResponseEntity.ok(mortgageService.checkMortgage(request));
    }
}
