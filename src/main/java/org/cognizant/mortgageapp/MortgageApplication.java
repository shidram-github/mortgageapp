package org.cognizant.mortgageapp;

import org.cognizant.mortgageapp.service.MortgageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MortgageApplication {

	@Autowired
	private MortgageService mortgageService;

	public static void main(String[] args) {
		SpringApplication.run(MortgageApplication.class, args);
	}
}
