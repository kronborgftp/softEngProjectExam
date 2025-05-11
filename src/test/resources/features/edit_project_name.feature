#written by Lasse
Feature: Edit project name

  Scenario: Successfully rename an existing project
    Given a project "P1" named "OldProject" exists
    When the user edits project name of "P1" to "NewProject"
    Then the project name for "P1" should be "NewProject"