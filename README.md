#FFmpeg

To use the video filming feature from this branch, you need to install  [FFmpeg](https://ffmpeg.org/download.html) on your machine.

Installation steps for Linux:

* `sudo apt-get update`
* `sudo apt-get install ffmpeg`
* `ffmpeg --version`

Installation steps for MacOS:
* `brew install ffmpeg`

[Installation steps for Windows](https://www.wikihow.com/Install-FFmpeg-on-Windows)

# Introduction
This repository demonstrates how to use our [Neodymium library](https://github.com/Xceptance/neodymium-library) to set up a maintainable and well structured test automation project. Please also see the [Wiki](https://github.com/Xceptance/neodymium-library/wiki) for the Neodymium library project which shows and explains how to use the vaious features from Neodymium library. There you will also find insight about how we think a test automation project should be structured and what results you should get out of it.

## Selenide
Within this example project we demonstrate how to set up and implement a test automation project using Selenide. You can find information about Selenide within our [Wiki](https://github.com/Xceptance/neodymium-library/wiki/Selenide).

## How to try it out
This is a short introduction on how to get it running. 

### Prerequisites
You will need the following to try it out:
* Git
* Maven 3+
* JDK 8
* IDE of your choice 
* Web browsers of your choice and their respective [WebDrivers](https://github.com/Xceptance/neodymium-library/wiki/How-to-set-up-a-WebDriver)

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
Please check our [Wiki](https://github.com/Xceptance/neodymium-library/wiki/Allure-reports) to get all the info you need.
 
# Template
If you like our ideas and you would like to start a test automation project using the Neodymium library, we prepared a template that can be used as a "Hello World" tutorial and as a good starting point for your own project.

Check out [Neodymium template](https://github.com/Xceptance/neodymium-template).

## License
MIT
