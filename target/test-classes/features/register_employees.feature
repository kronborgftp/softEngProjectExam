# written by kim
Feature: Employee Registration
  Desription: registration of employees
  Actors: users

  Background:
    Given model and employee controller are initialized

  Scenario: user registers successfully
    When an employee registers with initials "jdoe" and name "John Doe"
    Then an employee has registered as employee with initials "jdoe" and name "John Doe"

  Scenario: user registers with 5 or more characters, automatically shortening it
    When an employee registers with initials "jndoe" and name "John Doe"
    Then an employee has registered as employee with initials "jndo" and name "John Doe"

  Scenario: user registers with 0 characters
    When an employee registers with initials "" and name "John Doe"
    Then the error message "Cannot have empty initials" is given

  Scenario: user registers with empty spaces characters
    When an employee registers with initials " " and name "John Doe"
    Then the error message "Cannot have empty initials" is given

  Scenario: user registers with non-alphabetic characters, with 4 or less characters
    When an employee registers with initials "jd0e" and name "John Doe"
    Then the error message "Cannot use non-letters in initials" is given

  Scenario: user registers with existing initials
    Given an employee with initials "jdoe" and name "John Doe" is registered
    When an employee registers with initials "jdoe" and name "John Doe"
    Then the error message "Initials already registered" is given
