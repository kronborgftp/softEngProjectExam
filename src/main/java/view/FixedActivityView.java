package view;

import model.FixedActivity;
import model.TimeEntry;

import java.util.Collection;

public class FixedActivityView {
    public void printFixedActivities(Collection<FixedActivity> list) {
        System.out.println("\nAvailable Absence Types:");
        for (FixedActivity fa : list) {
            System.out.printf("- [%s] %s\n", fa.getId(), fa.getName());
        }
    }

    public void printTimeLogged(TimeEntry entry) {
        System.out.println("Marked " + entry.getEmployee().getInitials() +
                " as absent (" + entry.getActivity().getActivityName() + ") on " +
                entry.getDate());
    }

    public void printError(String message) {
        System.out.println("ERROR: " + message);
    }
}
