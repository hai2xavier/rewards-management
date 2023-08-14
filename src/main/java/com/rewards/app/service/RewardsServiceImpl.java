package com.rewards.app.service;

import com.rewards.app.constants.RewardsConstants;
import com.rewards.app.entity.Customer;
import com.rewards.app.entity.Order;
import com.rewards.app.model.Rewards;
import com.rewards.app.repository.CustomerRepository;
import com.rewards.app.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RewardsServiceImpl implements RewardsService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	OrderRepository orderRepository;


	/**
	 * This method calculates the rewards for given customer identifier
	 *
	 * @param customerId
	 * @return Rewards
	 */
	public Rewards getRewardsByCustomerId(Long customerId) {

		// Time range for calculation
		Timestamp previousMonth = getDateBasedOnOffSetDays(RewardsConstants.DAYS_OF_MONTH);
		Timestamp previousSecondMonth = getDateBasedOnOffSetDays(RewardsConstants.TWO_MONTHS * RewardsConstants.DAYS_OF_MONTH);
		Timestamp previousThirdMonth = getDateBasedOnOffSetDays(RewardsConstants.THREE_MONTHS * RewardsConstants.DAYS_OF_MONTH);

		// Getting the list of orders for past three months from database
		List<Order> previousMonthOrders = orderRepository.findAllByCustomerIdAndTransactionDateBetween(
				customerId, previousMonth, Timestamp.from(Instant.now()));
		List<Order> previousSecondMonthOrders = orderRepository
				.findAllByCustomerIdAndTransactionDateBetween(customerId, previousSecondMonth, previousMonth);
		List<Order> previousThirdMonthOrders = orderRepository
				.findAllByCustomerIdAndTransactionDateBetween(customerId, previousThirdMonth,
						previousSecondMonth);

		// Getting the reward points for past three months
		Long previousMonthRewardPoints = getRewardsPerMonth(previousMonthOrders);
		Long previousSecondMonthRewardPoints = getRewardsPerMonth(previousSecondMonthOrders);
		Long previousThirdMonthRewardPoints = getRewardsPerMonth(previousThirdMonthOrders);

		// Getting the customer details
		Customer customer = customerRepository.findByCustomerId(customerId);

		// Populating the reward data
		Rewards customerRewards = new Rewards(customerId, customer.getFirstName(), customer.getLastName(), previousMonthRewardPoints,
				previousSecondMonthRewardPoints, previousThirdMonthRewardPoints, previousMonthRewardPoints + previousSecondMonthRewardPoints + previousThirdMonthRewardPoints);
		return customerRewards;
	}

	private Long getRewardsPerMonth(List<Order> orderList) {
		return orderList.stream().map(order -> calculateRewards(order))
				.collect(Collectors.summingLong(rewardsValue -> rewardsValue.longValue()));
	}

	private Long calculateRewards(Order order) {
		if (order.getTransactionAmount() > RewardsConstants.REWARDS_FIFTY_POINTS && order.getTransactionAmount() <= RewardsConstants.REWARDS_HUNDRED_POINTS) {
			return Math.round(order.getTransactionAmount() - RewardsConstants.REWARDS_FIFTY_POINTS);
		} else if (order.getTransactionAmount() > RewardsConstants.REWARDS_HUNDRED_POINTS) {
			return Math.round(order.getTransactionAmount() - RewardsConstants.REWARDS_HUNDRED_POINTS) * 2
					+ (RewardsConstants.REWARDS_HUNDRED_POINTS - RewardsConstants.REWARDS_FIFTY_POINTS);
		} else
			return 0l;

	}

	public Timestamp getDateBasedOnOffSetDays(int days) {
		return Timestamp.valueOf(LocalDateTime.now().minusDays(days));
	}

}
