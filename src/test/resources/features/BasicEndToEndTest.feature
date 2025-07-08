Feature: Basic end to end Test

  Scenario: Basic end to end test
    Given I have launched the website under test
    And I set the check in date to today
    And I want to stay for 2 nights
    When I click the check availability button and select the Single room option
    Then I can reserve my room and populate the additional details
    And the confirmation details are displayed

  @Regression
  Scenario: Basic end to end test with random check in date
    Given I have launched the website under test
    And I set a random check in date
    And I want to stay for 2 nights
    When I click the check availability button and select the Single room option
    Then I can reserve my room and populate the additional details
    And the confirmation details are displayed
