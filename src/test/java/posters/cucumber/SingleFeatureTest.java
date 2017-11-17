package posters.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/posters/cucumber/features/Register.feature", glue = "posters", plugin =
{
  "pretty", // console output
  "html:target/cucumber-report/", // html report
})
public class SingleFeatureTest
{
}
