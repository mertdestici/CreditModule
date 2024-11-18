package com.inghub.creditmodule.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private int installmentsPaid;
    private BigDecimal totalPaid;
    private boolean isFullyPaid;
}

