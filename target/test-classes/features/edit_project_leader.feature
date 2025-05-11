Feature: Edit project leader

  Scenario: Successfully change an existing project’s leader
    Given a project "P1" with leader "JD" exists
    When the user edits leader of project "P1" to "ZZ"
    Then the project leader for "P1" should be "ZZ"