package bookie;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Calendar {
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;

    private User user;
    private String name;
    private List<Appointment> appointments;

    public Calendar(final User user, final String name) {
	this.user = user;
	this.name = name;
	appointments = new ArrayList<>();
    }

    public void show() {
	for (Appointment app : appointments) {
	    System.out.println(app);
	}
    }

    public void book(final LocalDate date, final LocalTime startTime, final LocalTime endTime, final String subject) {
	if (startTime.isAfter(endTime)) {
	    throw new IllegalArgumentException("Start time must preceed end time.");
	}
	if (date.isBefore(LocalDate.now())) {
	    throw new IllegalArgumentException("Cannot book past date.");
	}
	if (isAlreadyBooked(startTime, endTime, date)) {
	    throw new IllegalArgumentException("Time is already booked");
	}

	appointments.add(new Appointment(date, startTime, endTime, subject));

    }

    private boolean isTimeInSpan(LocalTime time, LocalTime startOfSpan, LocalTime endOfSpan) {
	if (time.isAfter(startOfSpan) && time.isBefore(endOfSpan)) {
	    return true;
	}
	return false;
    }


    private boolean overlapsBooking(LocalTime time, LocalDate date, List<Appointment> appointments) {
	for (Appointment app : appointments) {
	    if (app.getDate().compareTo(date) == 0 && isTimeInSpan(time, app.getStartTime(), app.getEndTime())) {
		return true;
	    }
	}
	return false;
    }

    private boolean isAlreadyBooked(LocalTime startTime, LocalTime endTime, LocalDate date) {
    /*
    	Checks every time between this appointment's starttime and endtime
    	and compares to every other appointment's starttime and endtime.
    	 */
	for (int hour = startTime.getHour(); hour <= endTime.getHour(); hour++) {
	    for (int minute = 0; minute < MINUTES_PER_HOUR; minute++) {
		if ((LocalTime.of(hour, minute).isAfter(startTime) && LocalTime.of(hour, minute).isBefore(endTime)) &&
		    overlapsBooking(LocalTime.of(hour, minute), date, appointments)) {
		    return true;
		}
	    }
	}
	return false;
    }
}
