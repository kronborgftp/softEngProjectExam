package hellocucumber;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(features = "features", 
	plugin = { "summary", "html:target/cucumber/wikipedia.html"}, 
	monochrome=true, 
	snippets = SnippetType.CAMELCASE, 
	glue = { "hellocucumber" }) // "dtu.library.acceptance_tests", "dtu.library.student_tests"})
public class TestJUnit5 {
	@Test
	public void testJUnit5() {
		System.out.println("JUnit 5");
		assertTrue(true);
	}

}
