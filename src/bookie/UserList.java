package bookie;

import java.util.ArrayList;
import java.util.List;

/**
 * Presents users in form of a list.
 */
public class UserList
{
    /*
    existingUsers is static because it is only to be initialized once.
      */
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
