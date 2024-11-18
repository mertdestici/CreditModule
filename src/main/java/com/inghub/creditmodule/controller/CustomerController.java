package com.inghub.creditmodule.controller;

import com.inghub.creditmodule.model.Customer;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.inghub.creditmodule.service.CustomerService;
import com.inghub.creditmodule.dto.CreateCustomerRequest;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CreateCustomerRequest request) {
        Customer customer = customerService.createCustomer(request);
        return ResponseEntity.ok(customer);
    }
}