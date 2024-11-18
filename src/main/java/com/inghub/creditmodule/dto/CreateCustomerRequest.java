package com.inghub.creditmodule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCustomerRequest {
    private String name;
    private String surname;
    private BigDecimal creditLimit;
    private BigDecimal usedCreditLimit;
}
