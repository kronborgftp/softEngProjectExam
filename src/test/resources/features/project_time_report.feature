# Created by kronborgpaludan at 07/05/2025
Feature: Project Time Report

  Scenario: Generate a time report for a specific project
    Given a project with ID "25001" exists
    And time entries have been logged for the project
    When I generate a project time report for "25001"
    Then the report should show the correct hours for each activity

  Scenario: Attempt to generate a time report for a non-existent project
    Given no project with ID "99999" exists
    When I generate a project time report for "99999"
    Then I should see an error message saying project "99999" was not found

