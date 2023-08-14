package com.rewards.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rewards.app.entity.Customer;
import com.rewards.app.model.Rewards;
import com.rewards.app.service.CustomerService;
import com.rewards.app.service.RewardsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RewardsManagementController.class)
public class RewardsManagementControllerTest {

    @MockBean
    private RewardsService rewardsService;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetRewardsData() throws Exception {
        Long customerId = 1000000010L;
        Rewards rewards = new Rewards(customerId, "aFirstName", "aLastName", 10, 10, 10, 30);
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setFirstName("aFirstName");
        customer.setLastName("aLastName");
        when(rewardsService.getRewardsByCustomerId(customerId)).thenReturn(rewards);
        when(customerService.getCustomerDetails(customerId)).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/{customerId}/rewards", 1000000010))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastMonthRewardPoints").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastSecondMonthRewardPoints").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastThirdMonthRewardPoints").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalRewards").isNotEmpty())
                .andDo(print());
    }

    @Test
    void testGetRewardsData_CustomerDataDoesNotExist() throws Exception {
        Long customerId = 1000000010L;
        Rewards rewards = new Rewards(customerId, "aFirstName", "aLastName", 10, 10, 10, 30);
        when(rewardsService.getRewardsByCustomerId(customerId)).thenReturn(rewards);
        when(customerService.getCustomerDetails(customerId)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/{customerId}/rewards", 1000000010))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void testGetRewardsData_RewardsDataDoesNotExist() throws Exception {
        Long customerId = 1000000010L;
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setFirstName("aFirstName");
        customer.setLastName("aLastName");
        when(rewardsService.getRewardsByCustomerId(customerId)).thenReturn(null);
        when(customerService.getCustomerDetails(customerId)).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/{customerId}/rewards", 1000000010))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
