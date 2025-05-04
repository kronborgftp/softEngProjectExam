# KIMS

Feature: Employees

  Scenario: Employee registers themselves
    When The employee John Doe registers with initials "jdoe" and name "John Doe"
    Then The employee John Doe has registered as employee with initials "jdoe" and name "John Doe"

  Scenario: Employee logs in
    When The employee John Doe logs in with initials "jdoe"
    Then The employee is logged in with initials "jdoe"



