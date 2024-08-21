package com.wynd.vop.ratingssvc.service.runner;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeSuite;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;

/**
 * The runner class contains all feature file entries that needs to be executed at runtime.
 * The annotations provided in the cucumber runner class will assist in bridging the
 * features to step definitions.
 */
@RunWith(Cucumber.class)
@CucumberOptions(strict = false, plugin = { "pretty",
		"html:target/site/cucumber-pretty", "json:target/site/cucumber.json" },
		features = { "src/inttest/resources/gov/va/bip/ratingssvc/feature" },
		glue = { "com.wynd.vop.ratingssvc.service.steps" })
public class RatingsSvcRunner extends AbstractTestNGCucumberTests {

	final Logger LOGGER = LoggerFactory.getLogger(RatingsSvcRunner.class);

	@BeforeSuite(alwaysRun = true)
	public void setUp() throws Exception {
		LOGGER.debug("setUp method");
	}

}
