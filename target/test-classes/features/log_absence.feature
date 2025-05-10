# Created by kronborgpaludan at
Feature: Log Absence

  Scenario: Successfully log a vacation absence
    Given an employee with initials "JD" is registered
    And the employee is logged in
    When the employee logs an absence of type "Vacation" from "01-05-2025" to "02-05-2025"
    Then 2 vacation absence entries should be created for employee "JD"

  Scenario: Attempt to log absence without being logged in
    Given an employee with initials "JD" is registered
    When no one is logged in
    And someone tries to log a "Sick Leave" absence from "06-05-2025" to "07-05-2025"
    Then no absence entries should be created for employee "JD"

  Scenario: Attempt to log absence with invalid absence type
    Given an employee with initials "JD" is registered
    And the employee is logged in
    When the employee tries to log an absence with invalid type "4" from "08-05-2025" to "09-05-2025"
    Then no absence entries should be created for employee "JD"

  Scenario: Attempt to log absence with invalid date input
    Given an employee with initials "JD" is registered
    And the employee is logged in
    When the employee tries to log a "Course" absence from "invalid-date" to "09-05-2025"
    Then no absence entries should be created for employee "JD"
