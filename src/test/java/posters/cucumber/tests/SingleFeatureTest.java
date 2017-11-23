package posters.cucumber.tests;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

//This runner is used for development purposes. So you can run a single cucumber test case over and over again
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/posters/cucumber/features/Browse.feature", glue = "posters", monochrome = true, plugin =
{
  "pretty", // console output
  "html:target/cucumber-report/", // html report
})
public class SingleFeatureTest
{
}
