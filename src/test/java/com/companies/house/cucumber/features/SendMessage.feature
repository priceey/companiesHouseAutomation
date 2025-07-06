Feature: Send a message Test

  @Regression
  Scenario: Test the application can be launched
    Given I have launched the website under test
    When I submit a message
    Then the message sent successfully text is displayed