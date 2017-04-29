package bookie;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
	User can = new User("Can");
	User hashem = new User("Hashem");

	Calendar canHome = new Calendar(can, "Home");
	Calendar canWork = new Calendar(can, "Work");

	Calendar hashemHome = new Calendar(hashem, "Jalla Home");
	Calendar hashemWork = new Calendar(hashem, "Jalla Work");

	TimeSpan span = new TimeSpan(LocalTime.of(16, 04), LocalTime.of(18, 31));
	TimeSpan span2 = new TimeSpan(LocalTime.of(19, 00), LocalTime.of(21, 00));
	TimeSpan span3 = new TimeSpan(LocalTime.of(11, 04), LocalTime.of(13, 00));

	canWork.book(LocalDate.of(2017, 07, 16), span, "Jobba");
	canHome.book(LocalDate.of(2017, 07, 16), span2, "Fika med Pär-Olof");
	canHome.book(LocalDate.of(2017, 07, 17), span3, "Köpa bästa bulle");

	hashemWork.book(LocalDate.of(2017, 07, 16), span, "Jobba");
	hashemHome.book(LocalDate.of(2017, 07, 16), span2, "El has bezazi");
	hashemHome.book(LocalDate.of(2017, 07, 17), span3, "Köpa bästa eire");

	LocalDate date1 = LocalDate.of(2017, 07, 17);
	LocalDate date2 = LocalDate.of(2017, 07, 16);
	if (date1.isAfter(date2) || span3.getStartTime().isBefore(span2.getStartTime())) {
	    System.out.println("funkar");
	}

	CalendarFrame calFrame = new CalendarFrame();
    }
}
