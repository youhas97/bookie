package bookie;

import java.util.Comparator;

/**
 * Case insensitive comparison between two users' names.
 */
public class UserComparator implements Comparator<User>
{
    @Override public int compare(final User u1, final User u2) {
	return u1.getName().compareToIgnoreCase(u2.getName());
    }
}
