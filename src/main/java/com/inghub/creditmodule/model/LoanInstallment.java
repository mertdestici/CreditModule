package com.inghub.creditmodule.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class LoanInstallment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loanId", nullable = false)
    private Loan loan;

    private BigDecimal amount;
    private BigDecimal paidAmount = BigDecimal.ZERO;
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private boolean isPaid = false;
}

