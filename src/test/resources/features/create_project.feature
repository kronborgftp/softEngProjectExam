# written by kim
Feature: Create projects 
  Description: Creating projects
  Actors: users, projects

Background: 
    Given the system is ready for project creation
    Given an employee has registered as employee with initials "jdoe" and name "John Doe"

  Scenario: user creates standard project
    When an employee creates project with ID "25001" name "First Project" start and end date "2025-01-01" and "2025-01-30"
    Then a project with ID "25001" name "First Project" start and end date "2025-01-01" and "2025-01-30" is created

  Scenario: user creates project with duplicate name
    Given a project with ID "25001" and name "First Project" exists
    When an employee creates project with ID "25001" name "First Project" start and end date "2025-01-01" and "2025-01-30"
    Then the error message "Project already exists" is given

  Scenario: user creates project with duplicate ID

#   Scenario: user creates project with name containing non-alphabetic characters
#     When an employee creates project with ID "25001" name "First Project!!!" start and end date "2025-01-01" and "2025-01-30"
#     Then the error message "Cannot create project with special characters" is given
