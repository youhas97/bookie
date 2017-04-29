package bookie;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static UserList existingUsers = UserList.getInstance();

    private String name;

    public User(final String name) {
	this.name = name;
	existingUsers.addUser(this);
    }

    @Override
    public String toString() {
	return name;
    }

    public String getName() {
	return name;
    }

    public static UserList getExistingUsers() {
	return existingUsers;
    }
}
