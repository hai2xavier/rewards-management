package com.rewards.app.controller;

import com.rewards.app.constants.HttpConstants;
import com.rewards.app.entity.Customer;
import com.rewards.app.exception.CustomerNotFoundException;
import com.rewards.app.exception.RewardsNotAvailableException;
import com.rewards.app.model.Rewards;
import com.rewards.app.service.CustomerService;
import com.rewards.app.service.RewardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Tag(name = "Rewards Management", description = "Rewards Management APIs")
@RestController
@RequestMapping("/customers")
public class RewardsManagementController {

    @Autowired
    RewardsService rewardsService;

    @Autowired
    CustomerService customerService;

    @Operation(
            summary = "Retrieve rewards by Customer Id",
            description = "This method gets the reward points for the given customer id",
            tags = { "rewards"})
    @ApiResponses({
            @ApiResponse(responseCode = HttpConstants.OK, content = { @Content(schema = @Schema(implementation = Rewards.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = HttpConstants.NOT_FOUND, description = "Customer data not available", content = { @Content(schema = @Schema(implementation = String.class), mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = HttpConstants.INTERNAL_ERROR, description = "Internal Error Occurred",content = { @Content(schema = @Schema(implementation = String.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
            @ApiResponse(responseCode = HttpConstants.NO_CONTENT, description = "Reward data not available", content = { @Content(schema = @Schema(implementation = String.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
    @GetMapping(value = "/{customerId}/rewards",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rewards> getCustomerRewards(@PathVariable("customerId") Long customerId){
        Customer customer = customerService.getCustomerId(customerId);
        if(Objects.isNull(customer)) {
            throw new CustomerNotFoundException();
        }
        Rewards customerRewards = rewardsService.getRewardsByCustomerId(customerId);
        if(Objects.isNull(customerRewards)) {
            throw new RewardsNotAvailableException();
        }
        return new ResponseEntity<>(customerRewards,HttpStatus.OK);
    }

}
