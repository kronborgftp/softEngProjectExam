# Created by kronborgpaludan at 07/05/2025
Feature: Project Time Report

  Scenario: Generate a time report for a specific project
    Given a project with ID "25001" exists
    And time entries have been logged for the project
    When I generate a project time report for "25001"
    Then the report should show the correct hours for each activity

