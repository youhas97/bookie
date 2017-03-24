package bookie;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

public class Appointment {
    private LocalDate date;
    private LocalTime startTime, endTime;
    private String subject;
    private Month month;

    public Appointment(final LocalDate date, final LocalTime startTime,
		       final LocalTime endTime, final String subject) {
	this.date = date;
	this.month = date.getMonth();
	this.startTime = startTime;
	this.endTime = endTime;
	this.subject = subject;
    }

    public static void main(String[] args) {
	LocalTime time = LocalTime.of(14,00);
	System.out.println(time);
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

    public String getSubject() {
	return subject;
    }
}
