package se.liu.ida.dx17.tddd78.bookie.Calendar;

import java.util.Comparator;

/**
 * Compares two appointments.
 * Returns a 0, negative or positive integer if the first argument
 * occurs on the same time, precedes or follows the second argument.
 */
public class AppointmentComparator implements Comparator<Appointment>
{
    @Override public int compare(final Appointment app1, final Appointment app2) {
	if (app1.getDate().isBefore(app2.getDate())) {
	    return -1;
	} else if (app1.getDate().isAfter(app2.getDate())) {
	    return 1;
	} else {
	    if (app1.getStartTime().isBefore(app2.getStartTime())) {
		return -1;
	    } else if (app1.getStartTime().isAfter(app2.getStartTime())) {
		return 1;
	    }
	    return 0;
	}
    }
}
