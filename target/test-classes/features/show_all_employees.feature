# Created by kronborgpaludan at
Feature: View all registered employees

  Scenario: Viewing all employees when some are registered
    Given the following employees are registered:
      | initials | name             |
      | JD       | John Doe         |
      | HB       | Hubert Baumeister |
      | WW       | Walter White     |
    When I request to view all employees
    Then I should see the following employees listed:
      | initials | name             |
      | JD       | John Doe         |
      | HB       | Hubert Baumeister |
      | WW       | Walter White     |

  Scenario: Viewing all employees when none are registered
    Given no employees are registered
    When I request to view all employees
    Then I should see a message "No employees found."
