# Feature: Create projects
#   Description: Creating projects
#   Actors: users, projects

#   Scenario: user creates project
#     When a user creates project with name "First Project"
#     Then a project named "First Project" is created

#   Scenario: user assigns project leader
#     Given a project with name "First Project" exists
#     And a user with initials "jdoe" and name "John Doe" is registered
#     When a user assigns employee with initials "jdoe" as project leader
#     Then the employee with initials "jdoe" is assigned as project leader

#   Scenario: user renames project
#     Given a project with name "First Project" exists
#     When a user renames project "First Project" to "Another Project"
#     Then the project "First Project" is now named "Another Project"

#   Scenario: user deletes project
#     Given a project with name "First Project" exists
#     When a user deletes project "First Project"
#     Then the project "First Project" is deleted


