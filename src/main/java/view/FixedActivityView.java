/**
 *
 *
 * @author Frederik
 */
package view;

import model.TimeEntry;

public class FixedActivityView {

    public void printTimeLogged(TimeEntry entry) {
        System.out.println("Marked " + entry.getEmployee().getInitials() +
                " as absent (" + entry.getActivity().getActivityName() + ") on " +
                entry.getDate());
    }

    public void printError(String message) {
        System.out.println("ERROR: " + message);
    }
}
