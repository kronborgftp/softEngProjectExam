# Created by kronborgpaludan at 07/05/2025
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

