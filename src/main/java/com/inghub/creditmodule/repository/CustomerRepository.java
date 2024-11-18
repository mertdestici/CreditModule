package com.inghub.creditmodule.repository;

import com.inghub.creditmodule.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

