# Created by kronborgpaludan at 07/05/2025
Feature: Show all logged hours

  Scenario: Display all logged time entries
    Given some time entries have been logged
    When I view all logged hours
    Then the system should print all logged time entries
