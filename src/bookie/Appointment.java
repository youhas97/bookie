package bookie;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

public class Appointment {
    private LocalDate date;
    private LocalTime startTime, endTime;
    private String subject;

    public Appointment(final LocalDate date, final LocalTime startTime,
		       final LocalTime endTime, final String subject) {
	this.date = date;
	this.startTime = startTime;
	this.endTime = endTime;
	this.subject = subject;
    }

    public static void main(String[] args) {
	LocalTime time = LocalTime.of(14,00);
	LocalTime time2 = LocalTime.of(16, 00);
	LocalDate date = LocalDate.of(2017, 03, 29);
	Appointment app = new Appointment(date, time, time2, "bästa bulle på java");
	System.out.println(time);
	System.out.println(app.getTimeSpan());
	System.out.println(app);
    }

    public LocalDate getDate() {
	return date;
    }

    public LocalTime getStartTime() {
	return startTime;
    }

    public LocalTime getEndTime() {
	return endTime;
    }

    public String getTimeSpan() {
	return startTime + "-" + endTime;
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
	return date.getMonth() + " " + date.getDayOfMonth() + " (" + date.getDayOfWeek() + ")" + ", " + date.getYear() + ": " +
	       subject + " (" + this.getTimeSpan() + ")";
    }
}
