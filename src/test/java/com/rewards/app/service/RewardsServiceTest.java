package com.rewards.app.service;

import com.rewards.app.entity.Customer;
import com.rewards.app.entity.Order;
import com.rewards.app.model.Rewards;
import com.rewards.app.repository.CustomerRepository;
import com.rewards.app.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class RewardsServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    RewardsServiceImpl rewardsService;

    Customer customer;

    @BeforeEach
    public void setUp(){
        customer = new Customer();
        customer.setCustomerId(10L);
        customer.setFirstName("aFirstName");
        customer.setLastName("aLastName");
    }

    @DisplayName("Test for Rewards Data, Ignoring data related to transactions as we use timestamp for data accuracy while querying")
    @Test
    public void testGetRewards(){
        Order order = new Order();
        order.setTransactionAmount(10);
        order.setCustomerId(10L);
        order.setOrderId(10L);
        order.setTransactionDate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));

        //When
        lenient().when(customerRepository.findByCustomerId(10L)).thenReturn(customer);
        lenient().when(orderRepository.findAllByCustomerIdAndTransactionDateBetween(10L,
                Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().minusDays(30)))).thenReturn(Arrays.asList(order));

        // Method call
        Rewards rewardsActual = rewardsService.getRewardsByCustomerId(10L);

        // Assertions
        Assertions.assertEquals("aFirstName", rewardsActual.firstName());
        Assertions.assertEquals("aLastName", rewardsActual.lastName());
        Assertions.assertEquals(10L, rewardsActual.customerId());
    }

}
