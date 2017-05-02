package bookie;

import java.util.ArrayList;
import java.util.List;

public class UserList
{
    private static List<User> existingUsers = new ArrayList<>();

    private static final UserList INSTANCE = new UserList();

    public static UserList getInstance() {return INSTANCE;}

    public void addUser(User user) {
	existingUsers.add(user);
	//sorts users on the fly.
	existingUsers.sort(new UserComparator());
    }

    public List<User> getExistingUsers() {
	return existingUsers;
    }
}
