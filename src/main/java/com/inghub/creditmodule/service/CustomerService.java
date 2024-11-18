package com.inghub.creditmodule.service;

import com.inghub.creditmodule.dto.CreateCustomerRequest;
import com.inghub.creditmodule.model.Customer;
import com.inghub.creditmodule.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CreateCustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setSurname(request.getSurname());
        customer.setCreditLimit(request.getCreditLimit());
        customer.setUsedCreditLimit(request.getUsedCreditLimit() != null ? request.getUsedCreditLimit() : BigDecimal.valueOf(
                0));

        return customerRepository.save(customer);
    }
}
