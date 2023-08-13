package com.rewards.app.service;

import com.rewards.app.entity.Customer;
import com.rewards.app.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	/**
	 * This method fetches the customer data for given customer identifier
	 *
	 * @param customerId
	 * @return Customer
	 */
	public Customer getCustomerId(Long customerId) {
		// Getting the customer details
		Customer customer = customerRepository.findByCustomerId(customerId);
		return customer;
	}

}
