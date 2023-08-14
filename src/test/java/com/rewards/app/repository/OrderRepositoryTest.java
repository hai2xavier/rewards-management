package com.rewards.app.repository;

import com.rewards.app.entity.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void testFindAllByCustomerIdAndTransactionDateBetween(){
        List<Order> orders = orderRepository.findAllByCustomerIdAndTransactionDateBetween(1000000003L,
                Timestamp.valueOf(LocalDateTime.now().minusDays(30)), Timestamp.valueOf(LocalDateTime.now()));
        Assertions.assertEquals(3, orders.size());
    }
}
