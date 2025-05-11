Feature: Edit time entry

  Scenario: Successfully edit a time entry
    Given an employee with initials "JD" with activity "A1" exists and is logged in
    And a time entry for activity "A1" of 2.0 hours on "10-05-2025" exists
    When the user edits that entry to 3.0 hours on "11-05-2025"
    Then the time entry for activity "A1" should be 3.0 hours on "11-05-2025"