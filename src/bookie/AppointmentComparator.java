package bookie;

import java.util.Comparator;

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
