Feature: Basic end to end Test

  @Regression @EndToEnd @Accessibility
  Scenario: Basic end to end test
    Given I have launched the website under test and have enabled accessibility testing
    And I want to book a Double room
    And I set the check in date to today
    And I want to stay for 3 nights
    And I confirm there is a room available or create a room for test
    When I click the check availability button and select the correct room option
    Then I can reserve my room and populate the additional details
    And the confirmation details are displayed
    And I can confirm that the booking message has been sent

  @Regression @EndToEnd
  Scenario: Basic end to end test with random check in date
    Given I have launched the website under test
    And I want to book a Single room
    And I set a random check in date
    And I want to stay for 2 nights
    And I confirm there is a room available or create a room for test
    When I click the check availability button and select the correct room option
    Then I can reserve my room and populate the additional details
    And the confirmation details are displayed
    And I can confirm that the booking message has been sent
