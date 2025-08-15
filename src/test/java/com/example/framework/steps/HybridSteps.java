package com.example.framework.steps;

import com.example.framework.config.ConfigReader;
import com.example.framework.drivers.DriverFactory;
import com.example.framework.pages.ExamplePage;
import com.example.framework.utils.ApiUtils;
import com.example.framework.utils.JsonSchemaValidatorUtil;
import com.example.framework.utils.TestDataManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import static io.restassured.RestAssured.given;

public class HybridSteps {

    private String apiTitle;
    private Response lastResponse;
    private final ExamplePage example = new ExamplePage();

    @Step("Call page via API and extract <title> from HTML")
    @Given("I request the title of {string} via API")
    public void i_request_the_title_via_api(String absoluteUrl) {
        lastResponse = given().when().get(absoluteUrl);
        String html = lastResponse.getBody().asString();
        String[] parts = html.split("<title>");
        if (parts.length > 1) {
            apiTitle = parts[1].split("</title>")[0].trim();
        } else {
            apiTitle = "";
        }
    }

    @Step("Open page in browser: {0}")
    @When("I open {string} in the browser")
    public void i_open_in_browser(String absoluteUrl) {
        example.open(absoluteUrl);
    }

    @Then("the page title should match the API title")
    public void the_page_title_should_match_api_title() {
        WebDriver driver = DriverFactory.getDriver();
        String uiTitle = driver.getTitle();
        Assertions.assertEquals(apiTitle, uiTitle, "UI title does not match API title");
    }

    // --- API JSON schema validation example ---
    @Given("I call the user API for id {int}")
    public void i_call_user_api(int id) {
        String apiBase = ConfigReader.get("api.baseUrl", "https://reqres.in");
        lastResponse = given().spec(ApiUtils.baseSpec(apiBase)).when().get("/api/users/" + id);
    }

    @Then("the response matches the user schema")
    public void response_matches_schema() {
        lastResponse.then().assertThat()
                .body(JsonSchemaValidatorUtil.matchesSchema("config/schema/user_schema.json"));
    }

    @Then("the first name from test data {string} equals the API first name")
    public void compare_testdata_with_api(String jsonPointer) {
        String expected = TestDataManager.get(jsonPointer);
        String actual = lastResponse.jsonPath().getString("data.first_name");
        Assertions.assertEquals(expected, actual, "First name mismatch between test data and API");
    }
}
