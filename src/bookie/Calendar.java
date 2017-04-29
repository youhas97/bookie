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

	user.addCalendar(this);
    }

    public void book(final LocalDate date, final TimeSpan span, final String subject) {
	if (date.isBefore(LocalDate.now())) {
	    throw new IllegalArgumentException("Cannot book past date.");
	}
	if (isAlreadyBooked(span, date)) {
	    throw new IllegalArgumentException("Time is already booked");
	}
	appointments.add(new Appointment(date, span, subject));
    }

    private boolean isTimeInSpan(LocalTime time, TimeSpan span) {
	if (time.isAfter(span.getStartTime()) && time.isBefore(span.getEndTime())) {
	    return true;
	}
	return false;
    }


    private boolean overlapsBooking(LocalTime time, LocalDate date, List<Appointment> appointments) {
	for (Appointment app : appointments) {
	    if (app.getDate().compareTo(date) == 0 && isTimeInSpan(time, app.getSpan())) {
		return true;
	    }
	}
	return false;
    }

    private boolean isAlreadyBooked(TimeSpan span, LocalDate date) {
    /*
    	Checks every time between this appointment's starttime and endtime
    	and compares to every other appointment's starttime and endtime.
    	 */
	for (int hour = span.getStartTime().getHour(); hour <= span.getEndTime().getHour(); hour++) {
	    for (int minute = 0; minute < MINUTES_PER_HOUR; minute++) {
		if ((LocalTime.of(hour, minute).isAfter(span.getStartTime()) &&
		     LocalTime.of(hour, minute).isBefore(span.getEndTime())) &&
		    overlapsBooking(LocalTime.of(hour, minute), date, appointments)) {
		    return true;
		}
	    }
	}
	return false;
    }

    static boolean isExistingCalendar(Calendar cal) {
	for (User user : User.getExistingUsers()) {
	    if (!user.getCalendars().isEmpty()) {
		for (Calendar calendar : user.getCalendars()) {
		    if (cal.name.equals(calendar.name)) {
			return true;
		    }
		}
	    }
	}
	return false;
    }

    public List<Appointment> getAppointments() {
	return appointments;
    }

    public User getUser() {
	return user;
    }

    @Override public String toString() {
	return name;
    }
}