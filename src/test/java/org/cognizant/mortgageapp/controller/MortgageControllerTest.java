package org.cognizant.mortgageapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cognizant.mortgageapp.model.MortgageCheckRequest;
import org.cognizant.mortgageapp.model.MortgageCheckResponse;
import org.cognizant.mortgageapp.model.MortgageRate;
import org.cognizant.mortgageapp.service.MortgageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MortgageController.class)
public class MortgageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MortgageService mortgageService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetInterestRates() throws Exception {
        List<MortgageRate> rates = Arrays.asList(new MortgageRate(1, 3.5, LocalDateTime.now()), new MortgageRate(2, 4.0, LocalDateTime.now()));
        when(mortgageService.getMortgageRates()).thenReturn(rates);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/interest-rates")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCheckMortgage() throws Exception {
        MortgageCheckRequest request = new MortgageCheckRequest(50000, 30, 200000, 250000);
        MortgageCheckResponse response = new MortgageCheckResponse(true, "3.5");

        String requestString = objectMapper.writeValueAsString(request);
        String responseString = objectMapper.writeValueAsString(response);

        when(mortgageService.checkMortgage(request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/mortgage-check")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseString));
    }

}