# Created by kronborgpaludan at 07/05/2025
Feature: Employee Time Report

  Scenario: Generate a time report for a specific employee
    Given an employee with initials "JD" exists
    And time entries have been logged for the employee
    When I generate a time report for "JD"
    Then the report should show the correct hours per day
