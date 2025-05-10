Feature: Employee Registration
  Desription: registration of employees
  Actors: users

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
    Then the error message "Cannot register empty initials" is given

  # Scenario: user registers with integers, with 4 or less characters
  #   When an employee registers with initials "1234" and name "John Doe"
  #   Then the error message "Cannot register initials with numbers" is given

  # Scenario: user registers with non-alphabetic characters, with 4 or less characters
  #   When an employee registers with initials "jd0e" and name "John Doe"
  #   Then the error message "Cannot register initials with numbers" is given

  Scenario: user registers with existing initials
    Given an employee with initials "jdoe" and name "John Doe" is registered
    When an employee registers with initials "jdoe" and name "John Doe"
    Then the error message "Initials already registered" is given

  # Scenario: user deletes employee initials successfully
  #   Given a user with initials "jdoe" and name "John Doe" is registered
  #   When a user deletes employee initials "jdoe"
  #   Then the initials "jdoe" no longer exists

  # Scenario: user deletes non-existent initials
  #   When a user deletes employee initials "waow"
  #   Then the error message "Cannot delete non-existent initials" is given
