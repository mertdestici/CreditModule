package com.inghub.creditmodule.service;

import com.inghub.creditmodule.model.*;
import com.inghub.creditmodule.dto.*;
import com.inghub.creditmodule.repository.CustomerRepository;
import com.inghub.creditmodule.repository.LoanInstallmentRepository;
import com.inghub.creditmodule.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final LoanInstallmentRepository installmentRepository;

    public LoanService(LoanRepository loanRepository, CustomerRepository customerRepository,
                       LoanInstallmentRepository installmentRepository) {
        this.loanRepository = loanRepository;
        this.customerRepository = customerRepository;
        this.installmentRepository = installmentRepository;
    }
    public Loan createLoan(CreateLoanRequest request) {
        Optional<Customer> optionalCustomer = customerRepository.findById(request.getCustomerId());
        if (optionalCustomer.isEmpty()) {
            throw new IllegalArgumentException("Customer not found with ID: " + request.getCustomerId());
        }

        Customer customer = optionalCustomer.get();
        BigDecimal totalAmount = request.getLoanAmount().multiply(BigDecimal.ONE.add(request.getInterestRate()));

        if (customer.getUsedCreditLimit().add(totalAmount).compareTo(customer.getCreditLimit()) > 0) {
            throw new IllegalArgumentException("Customer does not have enough credit limit.");
        }

        Loan loan = new Loan();
        loan.setCustomer(customer);
        loan.setLoanAmount(totalAmount);
        loan.setNumberOfInstallment(request.getNumberOfInstallments());

        loan = loanRepository.save(loan);

        List<LoanInstallment> installments = new ArrayList<>();
        BigDecimal installmentAmount = totalAmount.divide(BigDecimal.valueOf(request.getNumberOfInstallments()), 2, BigDecimal.ROUND_HALF_UP);

        for (int i = 1; i <= request.getNumberOfInstallments(); i++) {
            LoanInstallment installment = new LoanInstallment();
            installment.setLoan(loan);
            installment.setAmount(installmentAmount);
            installment.setDueDate(LocalDate.now().plusMonths(i).withDayOfMonth(1));
            installments.add(installment);
        }

        installmentRepository.saveAll(installments);

        customer.setUsedCreditLimit(customer.getUsedCreditLimit().add(totalAmount));
        customerRepository.save(customer);

        return loan;
    }
    public List<Loan> listLoansByCustomer(Long customerId) {
        return loanRepository.findByCustomerId(customerId);
    }

    public List<LoanInstallment> listInstallmentsByLoan(Long loanId) {
        return installmentRepository.findByLoanId(loanId);
    }

    public PaymentResponse payLoan(Long loanId, BigDecimal amount) {
        List<LoanInstallment> installments = installmentRepository.findByLoanId(loanId);

        BigDecimal remainingAmount = amount;
        int installmentsPaid = 0;
        BigDecimal totalPaid = BigDecimal.ZERO;

        for (LoanInstallment installment : installments) {
            if (installment.isPaid()) continue;
            if (installment.getDueDate().isAfter(LocalDate.now().plusMonths(3))) break;

            if (remainingAmount.compareTo(installment.getAmount()) >= 0) {
                remainingAmount = remainingAmount.subtract(installment.getAmount());
                installment.setPaidAmount(installment.getAmount());
                installment.setPaymentDate(LocalDate.now());
                installment.setPaid(true);
                totalPaid = totalPaid.add(installment.getAmount());
                installmentsPaid++;
            } else {
                break;
            }
        }

        installmentRepository.saveAll(installments);

        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new IllegalArgumentException("Loan not found"));
        boolean isFullyPaid = installments.stream().allMatch(LoanInstallment::isPaid);

        if (isFullyPaid) {
            loan.setPaid(true);
            loanRepository.save(loan);
        }

        return new PaymentResponse(installmentsPaid, totalPaid, isFullyPaid);
    }
}

