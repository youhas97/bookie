package bookie;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
	User bertil = new User("Bertil");
	Calendar cal = new Calendar(bertil, "TestCal");
	cal.book(LocalDate.of(2017, 07, 16), LocalTime.of(16, 04), LocalTime.of(18, 31), "Fika med Pär-Olof");
	cal.show();
	System.out.println(cal);

	CalendarFrame calFrame = new CalendarFrame();
    }
}
