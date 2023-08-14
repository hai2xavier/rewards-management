package com.rewards.app.service;

import com.rewards.app.entity.Customer;
import com.rewards.app.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerServiceImpl customerService;

    Customer customer;

    @BeforeEach
    public void setUp(){
        customer = new Customer();
        customer.setCustomerId(10L);
        customer.setFirstName("aFirstName");
        customer.setLastName("aLastName");
    }

    @DisplayName("JUnit test for saveEmployee method")
    @Test
    public void testGetCustomerDetails(){
        //When
        when(customerRepository.findByCustomerId(10L)).thenReturn(customer);

        // Method call
        Customer customerActual = customerService.getCustomerDetails(10L);

        // Assertions
        Assertions.assertEquals("aFirstName", customerActual.getFirstName());
        Assertions.assertEquals("aLastName", customerActual.getLastName());
        Assertions.assertEquals(10L, customerActual.getCustomerId());
    }

}
