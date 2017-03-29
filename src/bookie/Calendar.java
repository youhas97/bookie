package bookie;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Calendar {
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

	appointments.add(new Appointment(date, startTime, endTime, subject));
    }
}
