#written by Lasse
Feature: Edit project name

  Scenario: Successfully rename an existing project
    Given a project "P1" named "OldProject" exists
    When the user edits project name of "P1" to "NewProject"
    Then the project name for "P1" should be "NewProject"

  Scenario: Attempt to rename to an empty string
    Given a project "P1" named "OldProject" exists
    When the user edits project name of "P1" to ""
    Then I should see a project error message "Invalid name input."
    And the project name for "P1" should be "OldProject"

  Scenario: Attempt to rename to the same name
    Given a project "P1" named "OldProject" exists
    When the user edits project name of "P1" to "OldProject"
    Then I should see a project error message "Name must be different."
    And the project name for "P1" should be "OldProject"