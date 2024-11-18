package com.inghub.creditmodule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inghub.creditmodule.config.SecurityConfiguration;
import com.inghub.creditmodule.controller.LoanController;
import com.inghub.creditmodule.dto.CreateLoanRequest;
import com.inghub.creditmodule.model.Customer;
import com.inghub.creditmodule.model.Loan;
import com.inghub.creditmodule.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoanController.class)
@AutoConfigureMockMvc(addFilters = false)
class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    @Autowired
    private ObjectMapper objectMapper;

    private CreateLoanRequest createLoanRequest;
    private Loan loan;

    @BeforeEach
    void setUp() {
        createLoanRequest = new CreateLoanRequest();
        createLoanRequest.setCustomerId(1L);
        createLoanRequest.setLoanAmount(BigDecimal.valueOf(10000));
        createLoanRequest.setInterestRate(BigDecimal.valueOf(0.2));
        createLoanRequest.setNumberOfInstallments(12);

        // Test için Loan nesnesini hazırlıyoruz
        loan = new Loan();
        loan.setId(1L);
        loan.setCustomer(Customer.builder().id(1L).build());
        loan.setLoanAmount(BigDecimal.valueOf(12000)); // Faiz dahil tutar
        loan.setNumberOfInstallment(12);
        loan.setCreateDate(LocalDateTime.now());
        loan.setPaid(false);
    }

    @Test
    void testCreateLoan() throws Exception {
        Mockito.when(loanService.createLoan(Mockito.any(CreateLoanRequest.class)))
               .thenReturn(loan);

        mockMvc.perform(post("/loans")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createLoanRequest))
                                .header("Authorization", "Basic YWRtaW46YWRtaW5wYXNzd29yZA=="))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id", is(1)))
               .andExpect(jsonPath("$.loanAmount", is(12000)))
               .andExpect(jsonPath("$.numberOfInstallment", is(12)))
               .andExpect(jsonPath("$.paid", is(false)));
    }

    @Test
    void testListLoansByCustomer() throws Exception {
        Mockito.when(loanService.listLoansByCustomer(1L)).thenReturn(java.util.List.of(loan));

        mockMvc.perform(get("/loans")
                                .param("customerId", "1")
                                .header("Authorization", "Basic YWRtaW46YWRtaW5wYXNzd29yZA=="))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[0].id", is(1)))
               .andExpect(jsonPath("$[0].loanAmount", is(12000)));
    }
}

