@Smoke
Feature: Searching for products

  Scenario Outline: Searching for an existing product
    Given The browser "<browser>" is open
    And I am on the homepage of the Posters shop
    
    Then I want see the logo, the carousel and some hot products
    And the footer should be visible
    
    When I search for "<searchTerm>"
    Then I want to be on a category page
    And the page should show for the searchterm "<searchTerm>" "<expectedCount>" products
    
    When I click on the product number "<productPosition>"
    Then I want to be on a product detail page and see the "<productName>" as headline

    Examples: 
      | searchTerm | expectedCount | productPosition | productName                 | browser         |
      | bear       |             3 |               1 | Grizzly Bear                | Chrome_1024x768 |
      | bee        |             9 |               4 | Indian Summer: Orange Beech | FF_1024x768     |

  Scenario Outline: Searching for a non-existing product
    Given The browser "<browser>" is open
    And I am on the homepage of the Posters shop
    
    Then I want see the logo, the carousel and some hot products
    And the footer should be visible
    
    When I search for "Foobar"
    Then I want to be on a no hits page

    Examples: 
      | browser         |
      | Chrome_1024x768 |
      | FF_1024x768     |
