Feature: Assign project leader
  Description: assigning a leader to manage a project
  Actors: users, projects

  Background:
    Given there are several employees | "jdoe" | "huba" | "wawi" | registered
    And a project with name "First Project" exists


  Scenario: user assigns themselves as project leader
    When user "jdoe"



  Scenario: user assigns another user as project leader
    Given a user has registered as employee with initials "jdoe" and name "John Doe"
    And a user has registered as employee with initials "jdoe" and name "John Doe"



