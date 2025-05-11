#written by Lasse
Feature: Edit time entry

  Scenario: Successfully edit a time entry
    Given an employee with initials "JD" with activity "A1" exists and is logged in
    And a time entry for activity "A1" of 2.0 hours on "10-05-2025" exists
    When the user edits that entry to 3.0 hours on "11-05-2025"
    Then the time entry for activity "A1" should be 3.0 hours on "11-05-2025"

  Scenario: Attempt to edit with invalid selection
    Given an employee with initials "JD" with activity "A1" exists and is logged in
    And a time entry for activity "A1" of 2.0 hours on "10-05-2025" exists
    When the user selects entry number 2 to edit
    Then I should see a time entry error message "Cancelled or invalid selection."
    And the time entry for activity "A1" should be 2.0 hours on "10-05-2025"

  Scenario: Attempt to edit with non-numeric hours
    Given an employee with initials "JD" with activity "A1" exists and is logged in
    And a time entry for activity "A1" of 2.0 hours on "10-05-2025" exists
    When the user edits that entry to "abc" hours on "11-05-2025"
    Then I should see a time entry error message "Invalid hours input."
    And the time entry for activity "A1" should be 2.0 hours on "10-05-2025"

  Scenario: Attempt to edit with wrong half-hour precision
    Given an employee with initials "JD" with activity "A1" exists and is logged in
    And a time entry for activity "A1" of 2.0 hours on "10-05-2025" exists
    When the user edits that entry to 2.25 hours on "11-05-2025"
    Then I should see a time entry error message "Hours must be in half-hour increments."
    And the time entry for activity "A1" should be 2.0 hours on "10-05-2025"

  Scenario: Attempt to edit an absence entry
    Given an employee with initials "JD" with activity "A1" exists and is logged in
    And an absence entry for activity "A1" exists
    When the user selects entry number 1 to edit
    Then I should see a time entry error message "This is an absence entry. Editing is not supported."
