package bookie;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
	User abdi = new User("Test");
	Calendar cal = new Calendar(abdi, "TestCali");
	cal.book(LocalDate.of(2017, 07, 16), LocalTime.of(16, 04), LocalTime.of(18, 31), "Fika med Ahmed");
	cal.show();

	CalendarFrame calFrame = new CalendarFrame();
    }
}
