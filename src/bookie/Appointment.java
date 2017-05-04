package bookie;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

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

    public static void main(String[] args) {
	LocalTime time = LocalTime.of(14,00);
	LocalTime time2 = LocalTime.of(16, 00);
	LocalDate date = LocalDate.of(2017, 03, 29);
	Appointment app = new Appointment(date, new TimeSpan(time, time2), "bästa bulle på java");
	System.out.println(time);
	System.out.println(app.span);
	System.out.println(app);
    }

    public LocalDate getDate() {
	return date;
    }

    public LocalTime getStartTime() {
	return span.getStartTime();
    }

    public LocalTime getEndTime() {
	return span.getEndTime();
    }

    public TimeSpan getSpan() {
	return span;
    }

    public Month getMonth() {
	return date.getMonth();
    }

    public DayOfWeek getDay() {
	return date.getDayOfWeek();
    }

    public String getSubject() {
	return subject;
    }

    @Override public String toString() {
	return date.getMonth() + " " + date.getDayOfMonth() + ", " + date.getYear() + " (" + date.getDayOfWeek() + ")" + ": " +
	       subject + " (" + span + ")";
    }
}
