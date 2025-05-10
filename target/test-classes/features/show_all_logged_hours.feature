# Created by kronborgpaludan
Feature: Show all logged hours

  Scenario: Display only regular (work) time entries
    Given some work time entries have been logged
    When I view all logged hours
    Then the system should print all logged work time entries

  Scenario: Display only absence entries
    Given some absence entries have been logged
    When I view all logged hours
    Then the system should print all logged absence entries

  Scenario: Display both work and absence entries
    Given some work and absence time entries have been logged
    When I view all logged hours
    Then the system should print all logged work and absence entries

  Scenario: Viewing all logged hours when none are recorded
    Given no time entries have been logged
    When I view all logged hours
    Then the system should print "No time entries recorded."


