# Introduction
This repository demonstrates how to use our [Neodymium library](https://github.com/Xceptance/neodymium-library) to set up a maintainable and well structured test automation project. Furthermore we use the [Wiki](https://github.com/Xceptance/neodymium-library/wiki) on the Neodymium library project to show and explain how to use features from Neodymium library. We also give insight how we think a test automation project should be structured and what results you should get out of it.

## Selenide
Within this example project we demonstrate how to set up and implement a test automation project using Selenide. You can find information about Selenide within our [Wiki](https://github.com/Xceptance/neodymium-library/wiki/Selenide).

## How to try it out
This will be a short introduction how to get it running. 

### Prerequisites
You will need the following technologies available to try it out:
* Git
* Maven 3+
* JDK 8
* IDE of your choice 
* Web browser's of your choice and their respective [WebDrivers](https://github.com/Xceptance/neodymium-library/wiki/How-to-set-up-a-WebDriver)

### Get and run Posters
Posters is a web shop for demo purposes build by [Xceptance](https://www.xceptance.com/en/). We use it throughout all our software products to demonstrate their capabilities within a simple and stable environment. 
1. Download latest XLT [here](https://www.xceptance.com/en/xlt/download.html).
2. Go to [downloadPathXlt]/samples/app-server/bin/
3. Start with: ./start.sh
4. Go to [https://localhost:8443/posters/](https://localhost:8443/posters/) to check if it is running

### Get yourself a free copy
Simply clone or fork this project.

### IDE way of doing
1. Import the project as Maven project
2. Set up your [WebDrivers](https://github.com/Xceptance/neodymium-library/wiki/How-to-set-up-a-WebDriver)
3. Go to `posters.neodymium.tests.smoke`
4. Run `HomePageTest.java` as JUnit test.

### Get into the Console
1. Open a console of your choice
2. Go to the project folder
3. Run `mvn clean test`

### Test out Allure reports
Please check our [Wiki](https://github.com/Xceptance/neodymium-library/wiki/Allure-reports) to get all info you need.
 
# Template
If you like our ideas and you would like to start a test automation project using the Neodymium library. We prepared a template that can be used as "Hello World" tutorial and is a good starting point for your own project.

Check out [Neodymium template](https://github.com/Xceptance/neodymium-template).

## License
MIT