Feature: Searching for products

  Scenario Outline: Searching for an existing product
    Given I am on the homepage of the Posters shop in browser "<browser>"
    When I search for "<searchTerm>"
    Then the search result text should be "<title>"

    Examples: 
      | searchTerm | title                                            | browser         | 
      | bear       | Your results for your search: 'bear' (3 posters) | Chrome_1024x768 |
      | bee        | Your results for your search: 'bee' (9 posters)  | FF_1024x768     |

  Scenario Outline: Searching for a non-existing product
    Given I am on the homepage of the Posters shop in browser "<browser>"
    When I search for "Foobar"
    Then a message with text "Sorry! No results found matching your search. Please try again." should appear
    
    Examples:
      | browser         |
      | Chrome_1024x768 |
      | FF_1024x768     |
