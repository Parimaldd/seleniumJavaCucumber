package runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(

        features = "src/main/java/feature/BuyProduct.feature",       // path to your feature files
        glue = "stepDefinition",                   // package name of step definitions
        monochrome = true,                         // cleaner console output
        dryRun = false ,                            // true = check mapping, false = run
        plugin = {
                "pretty",                              // readable console output
                "html:target/cucumber-reports.html",   // generate HTML report
                "json:target/cucumber.json"            // optional JSON report
        }
)
public class TestRunner {

}
