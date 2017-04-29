package bookie;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
	User bertil = new User("Bertil");
	User anders = new User("Anders");
	User sven = new User("Sven");
	Calendar cal = new Calendar(bertil, "TestCal");
	TimeSpan span = new TimeSpan(LocalTime.of(16, 04), LocalTime.of(18, 31));
	TimeSpan span2 = new TimeSpan(LocalTime.of(19, 00), LocalTime.of(21, 00));
	TimeSpan span3 = new TimeSpan(LocalTime.of(11, 04), LocalTime.of(13, 00));
	cal.book(LocalDate.of(2017, 07, 16), span, "Fika med Pär-Olof");
	cal.book(LocalDate.of(2017, 07, 16), span2, "Fika med Pär-Olof");
	cal.book(LocalDate.of(2017, 07, 16), span3, "Fika med Pär-Olof");
	cal.show();
	System.out.println(cal);

	CalendarFrame calFrame = new CalendarFrame();
	calFrame.showCalendar(cal);
    }
}
