Feature: Employees
  Desription: actions pertaining to employees, and management of them
  Actors: employees

  Scenario: Employee registers
    When an employee registers with initials "jdoe" and name "John Doe"
    Then an employee has registered as employee with initials "jdoe" and name "John Doe"

  Scenario: Employee logs in
    When an employee logs in with initials "jdoe"
    Then an employee is logged in with initials "jdoe"

  # Scenario: Employee 


