@AllScenarios
Feature: Login Functionality
  As a user i want to login the page using the given credentials

  Scenario: Validation of login using valid credentials
    Given the user should be in login page
    When the user enter the username and password
    And the user press the login button
    Then it should be redirected to homepage

  Scenario: Validation of invalid login using invalid credentials
    Given the user should be in login page
    When the user enter the invalid username and password
    And the user press the login button
    Then it should throw the error