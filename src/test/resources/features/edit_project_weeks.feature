#written by Lasse
Feature: Edit project weeks

  Scenario: Successfully edit an existing project's weeks
    Given a project "P1" with duration 4 weeks exists
    When the user edits weeks of project "P1" to 6
    Then the project duration for "P1" should be 6 weeks

  Scenario: Attempt to edit with non-numeric input
    Given a project "P1" with duration 4 weeks exists
    When the user edits weeks of project "P1" to "abc"
    Then I should see a project weeks error message "Invalid weeks input."
    And the project duration for "P1" should be 4 weeks

  Scenario: Attempt to edit with negative input
    Given a project "P1" with duration 4 weeks exists
    When the user edits weeks of project "P1" to -2
    Then I should see a project weeks error message "Weeks must be positive."
    And the project duration for "P1" should be 4 weeks

  Scenario: Attempt to set end before start
    Given a project "P1" with duration 4 weeks exists
    When the user edits project "P1" start week to 1 and end week to 0
    Then I should see a project weeks error message "Weeks must be positive."
    And the project duration for "P1" should be 4 weeks

