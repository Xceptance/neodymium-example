Feature: Browse

Scenario Outline: Browsing the catalog
  Given The browser "<browser>" is open
  And I am on the homepage of the Posters shop

  Examples:
    | browser         |
    | Chrome_1024x768 |
