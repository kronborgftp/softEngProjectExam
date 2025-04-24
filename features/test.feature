Feature: Time registration for activities
Background:
    Given an employee "jdoe" exists
    And the employee "jdoe" is logged in
    And an activity "Design" exists
    And an activity "Programming" exists

Scenario: Employee registers time for an activity
    When the employee "jdoe" registers 3 hours on the activity "Design" for today
    Then the employee "jdoe" has 3 hours registered on the activity "Design" for today

Scenario: Employee attempts to register invalid time
    When the employee "jdoe" registers 25 hours on the activity "Programming" for today






