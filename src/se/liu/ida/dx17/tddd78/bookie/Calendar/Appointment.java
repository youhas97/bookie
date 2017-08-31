package se.liu.ida.dx17.tddd78.bookie.Calendar;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Handles rules and functionality for appointments.
 */
public class Appointment {
    private LocalDate date;
    private TimeSpan span;
    private String subject;

    public Appointment(final LocalDate date, final TimeSpan span, final String subject) {
	if (subject.isEmpty()) {
	    throw new IllegalArgumentException("Subject cannot be empty");
	}
	this.date = date;
	this.span = span;
	this.subject = subject;
    }

    public LocalDate getDate() {
	return date;
    }

    public LocalTime getStartTime() {
	return span.getStartTime();
    }

    public TimeSpan getSpan() {
	return span;
    }

    @Override public String toString() {
	return date.getMonth() + " " + date.getDayOfMonth() + ", " + date.getYear() + " (" + date.getDayOfWeek() + ")" + ": " +
	       subject + " (" + span + ")";
    }
}
