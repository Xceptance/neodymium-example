package posters.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/posters/cucumber/features", glue = "posters.cucumber", plugin =
{
  "pretty", // console output
  "html:target/cucumber-report/", // html report
})
public class RunAllFeaturesTest
{
}
