# Introduction
Using the [Neodymium library](https://github.com/Xceptance/neodymium-library) to set up a maintainable and well structured test automation project.

## Selenide
Within the project Selenide is used to set up the test automation suite. You can find information about Selenide within [Wiki](https://github.com/Xceptance/neodymium-library/wiki/Selenide).

## How to try it out
This is a short introduction on how to get it running. 

### Prerequisites
You will need the following:
* Git
* Maven 3+
* JDK 8
* IDE of your choice 
* Web browsers of your choice and their respective [WebDrivers](https://github.com/Xceptance/neodymium-library/wiki/How-to-set-up-a-WebDriver)

### IDE way of doing
1. Import the project as Maven project
2. Set up your [WebDrivers](https://github.com/Xceptance/neodymium-library/wiki/How-to-set-up-a-WebDriver)
3. Go to `posters.neodymium.tests.smoke`
4. Run `UserLoginTest.java` as JUnit test.

### Get into the Console
1. Open a console of your choice
2. Go to the project folder
3. Run `mvn clean test`

### Test out Allure reports
Please check out [Wiki](https://github.com/Xceptance/neodymium-library/wiki/Allure-reports) to get all the info you need.

# JUnit 5 vs. JUnit 4

Works with both at the moment. How to use this can be seen in the branch `update-to-neo-5-junit-4` of the original repo. 

## License
MIT
