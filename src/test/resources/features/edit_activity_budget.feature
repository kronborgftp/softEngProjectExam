#written by Lasse
Feature: Edit activity budget

  Scenario: Successfully edit an existing activity’s budget
    Given a project "P1" with activity "A1" with budget 100 hours exists
    When the user edits budget of activity "A1" to 150
    Then the activity budget for "A1" should be 150

  Scenario: Attempt to edit budget with non-numeric input
    Given a project "P1" with activity "A1" with budget 100 hours exists
    When the user edits budget of activity "A1" to "abc"
    Then I should see an error message "Invalid budget input."
    And the activity budget for "A1" should be 100

  Scenario: Attempt to edit budget with negative input
    Given a project "P1" with activity "A1" with budget 100 hours exists
    When the user edits budget of activity "A1" to -50
    Then I should see an error message "Budget must be non-negative."
    And the activity budget for "A1" should be 100