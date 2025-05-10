Feature: Edit activity budget

  Scenario: Successfully edit an existing activity’s budget
    Given a project "P1" with activity "A1" with budget 100 hours exists
    When the user edits budget of activity "A1" to 150
    Then the activity budget for "A1" should be 150