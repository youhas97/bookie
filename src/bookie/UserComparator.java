package bookie;

import java.util.Arrays;
import java.util.Comparator;

public class UserComparator implements Comparator<User>
{
    String[] userNames = new String[2];

    @Override public int compare(final User u1, final User u2) {
	return u1.getName().compareToIgnoreCase(u2.getName());
    }
}
