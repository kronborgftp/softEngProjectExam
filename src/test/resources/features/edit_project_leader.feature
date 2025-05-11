#written by Lasse
Feature: Edit project leader

  Scenario: Successfully change an existing project’s leader
    Given a project "P1" with leader "JD" exists
    And the system contains an employee with initials "ZZ"
    When the user edits leader of project "P1" to "ZZ"
    Then the project leader for "P1" should be "ZZ"

  Scenario: Attempt to set non-existent leader
    Given a project "P1" with leader "JD" exists
    When the user edits leader of project "P1" to "YY"
    Then I should see a leader-error message "Employee not found."
    And the project leader for "P1" should still be "JD"

  Scenario: Attempt to set empty initials
    Given a project "P1" with leader "JD" exists
    When the user edits leader of project "P1" to ""
    Then I should see a leader-error message "Invalid initials."
    And the project leader for "P1" should still be "JD"

