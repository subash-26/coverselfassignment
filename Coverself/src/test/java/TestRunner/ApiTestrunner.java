package TestRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
   //path of feature file
   features = "src\\test\\java\\Com\\Project\\Coverself\\Feature\\Api.Feature",
   //path of step definition file
   glue = "src\\main\\java\\Com\\Project\\StepDefinition\\Api.java"
   
   )
public class ApiTestrunner {

}
