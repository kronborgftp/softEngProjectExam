#written by Lasse
Feature: Edit activity weeks

  Scenario: Successfully edit an existing activity’s weeks
    Given a project "P1" with activity "A1" of duration 2 weeks exists
    When the user edits weeks of activity "A1" to 3
    Then the activity weeks for "A1" should be 3

  Scenario: Attempt to edit weeks with non-numeric input
    Given a project "P1" with activity "A1" of duration 2 weeks exists
    When the user edits weeks of activity "A1" to "foo"
    Then I should see a weeks-error message "Invalid week input."
    And the activity weeks for "A1" should be 2

  Scenario: Attempt to edit weeks with end before start
    Given a project "P1" with activity "A1" of duration 2 weeks exists
    When the user edits weeks of activity "A1" to 0
    Then I should see a weeks-error message "End week must be ≥ start week."
    And the activity weeks for "A1" should be 2
