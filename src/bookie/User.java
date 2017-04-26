package bookie;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private List<Calendar> calendars = new ArrayList<>();
    private static List<User> existingUsers = new ArrayList<>();

    public User(final String name, final List<Calendar> calendars) {
	this.name = name;
	this.calendars = calendars;
	existingUsers.add(this);
    }

    @Override
    public String toString() {
	return name;
    }

    public static List<User> getExistingUsers() {
	return existingUsers;
    }
}
