Feature: Register from user menu
  Description: Show case clean up steps implementation using dependency injection for more complex data passing

  @RegisterFromUserMenu
  Scenario Outline: Register a new customer via user menu
    Given The browser "<browser>" is open
    And I am on the homepage of the Posters shop
   
    And I am not logged in
    Then I want see the logo, the carousel and some hot products
    And the footer should be visible
    And the user will have: "<firstName>", "<lastName>", "<email>", "<password>"
   
    When I click the register button in the header
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
