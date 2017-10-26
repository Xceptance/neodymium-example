Feature: Homepage 

Scenario Outline: Visting the homepage
  Given I am on the homepage of the Posters shop in browser "<browser>"
  Then the page title should be "Posters - The Ultimate Online Shop"
  
  Examples:
    | browser         |
    | Chrome_1024x768 |
    | FF_1024x768     |