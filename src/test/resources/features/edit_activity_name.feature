#written by Lasse
Feature: Edit activity name

  Scenario: Successfully edit an existing activity’s name
    Given a project "P1" with activity "A1" named "OldName" exists
    When the user edits name of activity "A1" to "NewName"
    Then the activity name for "A1" should be "NewName"

  Scenario: Attempt to rename to an empty string
    Given a project "P1" with activity "A1" named "OldName" exists
    When the user edits name of activity "A1" to ""
    Then I should see a name‐error message "Invalid name input."
    And the activity name for "A1" should be "OldName"

  Scenario: Attempt to rename to the same name
    Given a project "P1" with activity "A1" named "OldName" exists
    When the user edits name of activity "A1" to "OldName"
    Then I should see a name‐error message "Name must be different."
    And the activity name for "A1" should be "OldName"
