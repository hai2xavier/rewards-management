package com.rewards.app.model;

public record Rewards (long customerId, String firstName, String lastName, long lastMonthRewardPoints, long lastSecondMonthRewardPoints,
					   long lastThirdMonthRewardPoints, long totalRewards) {
}
