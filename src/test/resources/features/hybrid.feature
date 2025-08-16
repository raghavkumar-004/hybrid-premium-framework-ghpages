@hybrid @smoke
Feature: Hybrid API + UI and API schema validation

  Scenario: Get page title via API and verify it in browser
    Given I request the title of "https://example.org" via API
    When I open "https://example.org" in the browser
    Then the page title should match the API title

  @api @schema
  Scenario: Validate user API schema and data
    Given I call the user API for id 2
    Then the response matches the user schema
    And the first name from test data "sampleUser.firstName" equals the API first name
