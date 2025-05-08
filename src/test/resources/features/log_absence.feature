# Created by kronborgpaludan at 08/05/2025
Feature: Log Absence

  Scenario: Successfully log a vacation absence for an employee
    Given an employee with initials "JD" is registered
    When the employee logs an absence of type "Vacation" from "01-05-2025" to "02-05-2025"
    Then 2 vacation absence entries should be created for employee "JD"

  Scenario: Attempt to log absence for non-existent employee
    Given no employee is registered with initials "XYZ"
    When someone tries to log a "Sick Leave" absence from "06-05-2025" to "07-05-2025" using initials "XYZ"
    Then no absence entries should exist for employee "XYZ"

  Scenario: Attempt to log absence with invalid absence type
    Given an employee with initials "JD" is registered
    When the employee tries to log an absence with invalid type "4" from "08-05-2025" to "09-05-2025"
    Then no absence entries should be created for employee "JD"

  Scenario: Attempt to log absence with invalid date input
    Given an employee with initials "JD" is registered
    When the employee tries to log a "Course" absence from "invalid-date" to "09-05-2025"
    Then no absence entries should be created for employee "JD"
