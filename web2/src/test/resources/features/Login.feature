@XIP-6
@REQ_XIP-1
Feature: Login Feature
    Verify if user is able to Login in to the site

    @TEST_XIP-7
    Scenario: Login with valid credentials
        Given I as a admin user navigate to Home page
        When I try to login with username "Admin" and password "Admin123"
        Then I am logged in successfully
    @TEST_XIP-8
    Scenario: Login with invalid credentials
        Given I as a admin user navigate to Home page
        When I try to login with username "Admin1" and password "Admin1234"
        Then I am given an error message