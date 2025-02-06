package org.cognizant.mortgageapp.exception;

public record ErrorDetails(int statusCode, String message, String details) {
}