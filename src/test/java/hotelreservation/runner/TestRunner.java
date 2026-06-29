package hotelreservation.runner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "hotelreservation.steps",
        plugin = {"pretty","html:target/cucumber-html-report"}
)


public class TestRunner {
}
