package bookie;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private List<Calendar> calendars = new ArrayList<>();
    private UserList existingUsers = new UserList();

    public User(final String name) {
	if (userExists(name)) {
	    throw new UnsupportedOperationException("A user with this name already exists");
	}
	this.name = name;
	existingUsers.addUser(this);
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

    @Override public String toString() {
	return name;
    }

    public void addCalendar(Calendar cal) {
	if (!Calendar.isExistingCalendar(cal)) {
	    calendars.add(cal);
	} else throw new IllegalArgumentException("Calendar already exists");
    }
}
