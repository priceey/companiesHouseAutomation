Feature: Validation confirmation

  @Regression @Validation
  Scenario: I can validate the basic end to end journey
    And I want to book a Suite room
    Given I have launched the website under test
    And I can verify and validate the home page
    When I set a random check in date
    And I want to stay for 2 nights
    And I confirm there is a room available or create a room for test
    And I click the check availability button and select the correct room option
    Then I can verify and validate the room page