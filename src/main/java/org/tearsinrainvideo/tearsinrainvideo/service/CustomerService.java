package org.tearsinrainvideo.tearsinrainvideo.service;

import org.tearsinrainvideo.tearsinrainvideo.model.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    Optional<Customer> findCustomerById(Long id);
    List<Customer> findAllCustomers();
    void deleteCustomer(Long id);
}
