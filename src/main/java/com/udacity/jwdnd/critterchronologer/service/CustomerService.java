package com.udacity.jwdnd.critterchronologer.service;

import com.udacity.jwdnd.critterchronologer.entity.Customer;
import com.udacity.jwdnd.critterchronologer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * This is service class for Customer.
 */
@Service
@Transactional
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Returns Customer Object based on ID.
     * @param customerId
     * @return cutomer object or null if not exist
     */
    public Customer getCustomerById(Long customerId){
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if(customerOpt != null) {
            return customerOpt.get();
        } else return null;
    }

    /**
     * Returns all customers in the database.
     * @return list of customers.
     */
    public List<Customer> retrieveAllCustomers(){
        return (List<Customer>) customerRepository.findAll();
    }

    /**
     * Saves customer object in database.
     * @param customer
     * @return updated saved customer object
     */
    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    /**
     * Deletes Customer  for the specified id.
     * @param id customer id
     */
    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }
}
