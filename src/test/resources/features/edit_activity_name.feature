#written by Lasse
Feature: Edit activity name

  Scenario: Successfully rename an existing activity
    Given a project "P1" with activity "A1" named "OldName" exists
    When the user renames activity "A1" to "NewName"
    Then the activity name for "A1" should be "NewName"
