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
    private static List<Calendar> calendars = new ArrayList<>();

    public Calendar(final User user, final String name) {
	this.user = user;
	this.name = name;
	appointments = new ArrayList<>();
	if (isExistingCalendar(this)) {
	    System.out.println("wadwadawdwa");
	    throw new IllegalArgumentException("this calendar already exists.");
	}
	calendars.add(this);
    }

    public void show() {
	for (Appointment app : appointments) {
	    System.out.println(app);
	}
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

    @Override public boolean equals(Object other) {
	if (other instanceof Calendar) {
	    System.out.println("equals");
	    return equalsCalendar((Calendar) other);
	}
	return false;
    }

    public static void main(String[] args) {
	User bertil = new User("Bertil");
	Calendar cal = new Calendar(bertil, "TestCal");
	Calendar cal2 = new Calendar(bertil, "TestCal");
    }

    private boolean equalsCalendar(Calendar cal) {
	if (name.equals(cal.name) && user.equals(cal.user)) {
	    System.out.println("equalscalendar");
	    return true;
	} else return false;
    }

    static boolean isExistingCalendar(Calendar cal) {
	if (!calendars.isEmpty() && calendars != null) {
	    for (Calendar calendar : calendars) {
		if (cal.equals(calendar)) {
		    System.out.println("existingcalendar");
		    return true;
		}
	    }
	}
	return false;
    }

    public List<Appointment> getAppointments() {
	return appointments;
    }

    @Override public String toString() {
	return user + ", " + name;
    }
}
