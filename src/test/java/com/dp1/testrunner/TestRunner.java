package com.dp1.testrunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/dp1/features",
        glue = {"com.dp1.StepDefinitions","com.framework.hooks"},
        plugin = {"pretty", "html:target/cucumber-reports.html", "json:target/cucumber.json"},
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @BeforeClass
    public void setUp() {
        String env = System.getProperty("testEnv", "stage");
        System.setProperty("testEnv", "dp1."+env);
        System.out.println("🌍 Running tests on environment: " + env);
    }

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
