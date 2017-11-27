Feature: Browse

  Scenario Outline: Browsing the catalog
    Given The browser "<browser>" is open
    And I am on the homepage of the Posters shop
    
    When I hover over "<categoryName>" and click on "<subCategoryName>"
    Then I want to be on a category page and see the "<subCategoryName>" as headline
    
    When I click on the product "<productName>"
    Then I want to be on a product detail page and see the "<productName>" as headline

    Examples: 
      | browser         | categoryName    | subCategoryName | productName            |
      | Chrome_1024x768 | Transportation  | Air Travel      | Air Lingus Airbus A320 |
      | Chrome_1024x768 | World of Nature | Animals         | Grizzly Bear           |
      | Chrome_1024x768 | Dining          | Main Dishes     | Tuna Steak             |
      | Chrome_1024x768 | Dining          | Sweets          | Colored Sprinkles      |
