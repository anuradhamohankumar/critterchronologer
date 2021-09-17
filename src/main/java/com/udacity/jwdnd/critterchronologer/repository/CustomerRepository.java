package com.udacity.jwdnd.critterchronologer.repository;

import com.udacity.jwdnd.critterchronologer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class for Customer Object or Table to do CURD operations.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {}
