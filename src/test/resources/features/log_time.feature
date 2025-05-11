# written by Frederik
Feature: Log time

  Scenario: Successfully log time for an assigned activity
    Given an employee with initials "JD" is registered and logged in
    And a project with ID "P1" and activity "A1" is available
    When the employee logs 3.5 hours on activity "A1"
    Then a time entry of 3.5 hours should exist for "JD" on activity "A1"

  Scenario: Fail to log time due to invalid activity ID
    Given an employee with initials "JD" is registered and logged in
    And a project with ID "P1" and activity "A1" is available
    When the employee tries to log 2.0 hours on invalid activity ID "ZZZ"
    Then no time entry should exist for employee "JD"


  Scenario: Fail to log time due to invalid hour format
    Given an employee with initials "JD" is registered and logged in
    And a project with ID "P1" and activity "A1" is available
    When the employee tries to log "2.333" hours on activity "A1"
    Then no time entry should exist for employee "JD"

  Scenario: Fail to log time due to non-numeric input
    Given an employee with initials "JD" is registered and logged in
    And a project with ID "P1" and activity "A1" is available
    When the employee tries to log "two" hours on activity "A1"
    Then no time entry should exist for employee "JD"





