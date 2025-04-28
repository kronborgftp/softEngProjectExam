#Laves af Lasse

Feature: Edit registered time

  Background:
    Given an employee "jdoe" exists
    And the employee "jdoe" is logged in
    And 4 hours are registered for "jdoe" on the activity "Analysis" for today

  Scenario: Employee edits registered time for an activity
    When the employee "jdoe" changes the registered time to 3 hours on the activity "Analysis" for toda
    Then the employee "jdoe" has 3 hours registered on the activity "Analysis" for today