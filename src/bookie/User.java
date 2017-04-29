package bookie;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private static List<User> existingUsers = new ArrayList<>();

    public User(final String name) {
	this.name = name;
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
