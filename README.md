**                    Rewards Management Application:
**

**Pre-requisites to run the application:
**

1. Jdk 17 & above
2. Maven 3.8 & above
3. H2 Database Engine

**Steps to run the application:**

1. Run mvn clean install
2. Run RewardsManagementApplication

**Sample Request: 
**
http://localhost:8080/customers/1000000003/rewards

**Sample Response:**

{
  "customerId" : 1000000003,
  "firstName" : "Harry",
  "lastName" : "Potter",
  "lastMonthRewardPoints" : 1904,
  "lastSecondMonthRewardPoints" : 360,
  "lastThirdMonthRewardPoints" : 54,
  "totalRewards" : 2318
}

**Open-API URL:
**

http://localhost:8080/swagger-ui/index.html#

**H2 Console:
**
http://localhost:8080/h2-ui/login.do

