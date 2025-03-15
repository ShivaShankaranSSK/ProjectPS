package com.PROJECTPS.testrunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = "src\\test\\java\\com\\PROJECTPS\\features\\PS01_Login.feature",
		glue = {"com.PROJECTPS.stepdefinition", "hooks"},
		dryRun = true,
		monochrome = true, 
		plugin = {"pretty", "html:target/cucumber-report.html"}
		// tags = "AllScenarios"
		)
public class RunnerClass extends AbstractTestNGCucumberTests {

	
	
}
