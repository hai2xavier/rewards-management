package com.rewards.app.repository;

import com.rewards.app.entity.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    public void testFindByCustomerId(){
        Customer actualCustomer = customerRepository.findByCustomerId(1000000001L);
        Assertions.assertEquals(1000000001L, actualCustomer.getCustomerId());
        Assertions.assertEquals("Captain", actualCustomer.getFirstName());
        Assertions.assertEquals("America", actualCustomer.getLastName());
    }
}
