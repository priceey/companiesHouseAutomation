Feature: Basic end to end Test

  @Regression
  Scenario: Test the application can be launched
    Given I have launched the website under test
    And I set the check in date to today
    And I want to stay for 2 nights
    When I click the check availability button and select the Single room option
    Then I can reserve my room and populate the additional details
    And the confirmation details are displayed
