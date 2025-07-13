Feature: Send a message Test

  @Regression @Messages
  Scenario: Test a message can be sent
    Given I have launched the website under test
    When I submit a message
    Then the message sent successfully text is displayed
    And the message is received in the back end