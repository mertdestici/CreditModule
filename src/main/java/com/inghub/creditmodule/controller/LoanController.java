package com.inghub.creditmodule.controller;

import com.inghub.creditmodule.dto.CreateLoanRequest;
import com.inghub.creditmodule.dto.PaymentResponse;
import com.inghub.creditmodule.model.*;
import com.inghub.creditmodule.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/loans")
@AllArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody CreateLoanRequest request) {
        Loan loan = loanService.createLoan(request);
        return ResponseEntity.ok(loan);
    }

    @GetMapping
    public ResponseEntity<List<Loan>> listLoansByCustomer(@RequestParam Long customerId) {
        List<Loan> loans = loanService.listLoansByCustomer(customerId);
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/{loanId}/installments")
    public ResponseEntity<List<LoanInstallment>> listInstallments(@PathVariable Long loanId) {
        List<LoanInstallment> installments = loanService.listInstallmentsByLoan(loanId);
        return ResponseEntity.ok(installments);
    }

    @PostMapping("/{loanId}/pay")
    public ResponseEntity<PaymentResponse> payLoan(@PathVariable Long loanId, @RequestParam BigDecimal amount) {
        PaymentResponse response = loanService.payLoan(loanId, amount);
        return ResponseEntity.ok(response);
    }
}

