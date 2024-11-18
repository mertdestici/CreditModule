package com.inghub.creditmodule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateLoanRequest {
    private Long customerId;
    private BigDecimal loanAmount;
    private BigDecimal interestRate;
    private int numberOfInstallments;
}
