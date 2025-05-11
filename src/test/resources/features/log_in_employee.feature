#written by Kim
Feature: Employee log in
  Desription: actions pertaining to employees, and management of them
  Actors: users

  Background: 
    Given an employee with initials "jdoe" and name "John Doe" is registered

  Scenario: user logs in with existing initials
    When an employee logs in with initials "jdoe"
    Then the employee "jdoe" is logged in with initials "jdoe"

  # Scenario: user logs in with non-existant initials
  #   When an employee logs in with initials "wawi"
  #   Then the error message "employee initials not found" is given

  Scenario: user logs out
    Given an employee is logged in with initials "jdoe"
    When the employee logs out
    Then the employee initials "jdoe" is logged out



