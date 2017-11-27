@Smoke
Feature: Homepage

  Scenario Outline: Visting the homepage
    Given The browser "<browser>" is open
    And I am on the homepage of the Posters shop
    
    Then the page title should be "Posters - The Ultimate Online Shop"
    And I want see the logo, the carousel and some hot products
    And the footer should be visible

    Examples: 
      | browser         |
      | Chrome_1024x768 |
      | FF_1024x768     |
