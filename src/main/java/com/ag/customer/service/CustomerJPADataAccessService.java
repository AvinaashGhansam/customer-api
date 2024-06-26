package com.ag.customer.service;

import com.ag.customer.repository.CustomerRepository;
import com.ag.customer.repository.CustomerDao;
import com.ag.customer.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class CustomerJPADataAccessService implements CustomerDao {
    private final CustomerRepository customerRepository;

    public CustomerJPADataAccessService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    @Override
    public List<Customer> selectAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return customerRepository.findById(Math.toIntExact(id));
    }

    @Override
    public void insertCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return customerRepository.existsCustomerByEmail(email);
    }

    @Override
    public void deleteCustomerById(Integer id) {
        customerRepository.deleteById(Math.toIntExact(id));
    }

    @Override
    public boolean existsPersonById(Integer id) {
       return customerRepository.existsCustomerById(id);
    }

    @Override
    public void updateCustomer(Customer updatedCustomer) {
        // update the customer
        customerRepository.save(updatedCustomer);
    }
}
