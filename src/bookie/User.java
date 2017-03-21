package bookie;

public class User {
    private String name;

    public User(final String name) {
	this.name = name;
    }

    @Override
    public String toString() {
	return name;
    }
}
