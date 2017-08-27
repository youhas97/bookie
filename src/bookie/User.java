package bookie;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles Functionality and rules for Users.
 */
public class User {
    private String name;
    private String password;
    private List<Calendar> calendars = new ArrayList<>();

    public User(final String name, final String password) {
	if (name.isEmpty()) {
	    throw new IllegalArgumentException("User must have a name!");
	}

	this.password = password;
	this.name = name;
    }

    /**
     * Alternate constructor for the class.
     * @param name Name of the user
     */
    public User(final String name) {
	this(name, "");
    }


    public void deleteCalendar(Calendar cal) {
	if (Calendar.isExistingCalendar(cal)) {
	    calendars.remove(cal);
	} else throw new IllegalArgumentException("This calendar does not exist!");
    }

    public List<Calendar> getCalendars() {
	return calendars;
    }

    public String getName() {
	return name;
    }

    public String getPassword() {
	return password;
    }

    @Override public String toString() {
	return name;
    }

    public void addCalendar(Calendar cal) {
	if (!Calendar.isExistingCalendar(cal)) {
	    calendars.add(cal);
	} else throw new IllegalArgumentException("A calendar with this name already exists");
    }
}
