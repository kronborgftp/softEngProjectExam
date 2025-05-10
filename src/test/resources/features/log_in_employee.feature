# Feature: Employee log in
#   Desription: actions pertaining to employees, and management of them
#   Actors: users

#   Background: 
#     Given a user has registered as employee with initials "jdoe" and name "John Doe"

#   Scenario: user logs in with existing initials
#     When a user logs in with initials "jdoe"
#     Then user is logged in with initials "jdoe"

#   Scenario: user logs in with non-existant initials
#     When a user logs in with initials "wawi"
#     Then the error message "employee initials not found" is given

#   Scenario: user logs out
#     Given a user is logged in with initials "jdoe"
#     When user logs out
#     Then the employee initials "jdoe" is logged out



