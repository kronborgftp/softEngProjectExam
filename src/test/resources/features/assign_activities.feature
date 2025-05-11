#Written by Kim
Feature: Create activities
   Description: Creating and assigning activities associated with certain projects
   Actors: users, projects, activities

   Background:
     Given a project with name "First Project" exists
     And a user with initials "jdoe" and name "John Doe" is registered

   Scenario: user creates and assigns activity to a project
     When a user creates activity for "Boring activity" to project "First Project"
     Then the activity "Boring Activity" is created for project "First Project"

   Scenario: user is assigned to activity
     Given an activity "Boring Activity" is assigned to project "First Project"
     When employee "jdoe" is assigned to the activity
     Then the activity has employee "jdoe" assigned to it

   Scenario: user unassigns and deletes activity from project
     Given an activity "Less Boring Activity" is assigned to project "First Project"
     When a user deletes activity "Less Boring Activity" from project "First Project"





