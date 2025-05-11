#written by Lasse
Feature: Edit project weeks

  Scenario: Successfully edit an existing project's weeks
    Given a project "P1" with duration 4 weeks exists
    When the user edits weeks of project "P1" to 6
    Then the project duration for "P1" should be 6 weeks
