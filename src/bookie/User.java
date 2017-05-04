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
    private UserList existingUsers = new UserList();

    public User(final String name, final String password) {
	if (userExists(name)) {
	    throw new UnsupportedOperationException("A user with this name already exists");
	}
	if (name.isEmpty()) {
	    throw new IllegalArgumentException("User must have a name!");
	}

	this.password = password;
	this.name = name;

	existingUsers.addUser(this);
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
	    this.calendars.remove(cal);
	} else throw new IllegalArgumentException("This calendar does not exist!");
    }

    public boolean userExists(String name) {
	for (User user : existingUsers.getExistingUsers()) {
	    if (name.equals(user.name)) {
		return true;
	    }
	}
	return false;
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
	} else throw new IllegalArgumentException("Calendar already exists");
    }
}
