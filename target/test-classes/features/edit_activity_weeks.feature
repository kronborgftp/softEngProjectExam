Feature: Edit activity weeks

  Scenario: Successfully edit an existing activity’s weeks
    Given a project "P1" with activity "A1" of duration 2 weeks exists
    When the user edits weeks of activity "A1" to 3
    Then the activity weeks for "A1" should be 3