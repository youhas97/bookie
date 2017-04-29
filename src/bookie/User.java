package bookie;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private List<Calendar> calendars = new ArrayList<>();
    private static List<User> existingUsers = new ArrayList<>();

    public User(final String name) {
	this.name = name;
	existingUsers.add(this);
    }

    public List<Calendar> getCalendars() {
	return calendars;
    }

    public String getName() {
	return name;
    }

    public static List<User> getExistingUsers() {
	return existingUsers;
    }

    @Override public String toString() {
	return name;
    }

    public void addCalendar(Calendar cal) {
	if (!Calendar.isExistingCalendar(cal)) {
	    calendars.add(cal);
	    System.out.println("Calendar created");
	} else System.out.println("Calendar already exists");
    }
}
