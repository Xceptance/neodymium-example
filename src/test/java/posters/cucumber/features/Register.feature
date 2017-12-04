@Smoke @Skip
Feature: Register
  Description: Show case clean up steps implementation using regex and scenario name for simple data passing

  #The parameters within the scenario outline cause an error in allure report that leads to not generating data for this feature
  #see: https://github.com/allure-framework/allure-cucumberjvm/issues/52
  @Register
  Scenario Outline: Register a new customer with "<email>" and "<password>"
    Given The browser "<browser>" is open
    And I am on the homepage of the Posters shop
    And I am not logged in
    
    Then I want see the logo, the carousel and some hot products
    And the footer should be visible
    
    When I click the login button
    Then I want to be on the login page
    
    When I click the register button
    Then I want to be on the register page
    
    When I fill the register form with "<firstName>", "<lastName>", "<email>", "<password>", "<password>" and send it
    Then I want to be registered successfully
    And I want to be on the login page
    
    When I fill the register form with "<email>" and "<password>" and send it
    Then I want to be logged in successfully with "<firstName>"

    Examples: 
      | browser         | firstName | lastName | email          | password  |
      | Chrome_1024x768 | Jane      | Doe      | jane@doe.com   | topsecret |
      | Chrome_1024x768 | Jim       | Doe      | jim@doe.com    | topsecret |
      | Chrome_1024x768 | Jeremy    | Doe      | jeremy@doe.com | topsecret |
