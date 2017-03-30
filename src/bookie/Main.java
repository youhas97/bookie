package bookie;

import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
	User abdi = new User("Abdi");
	Calendar cal = new Calendar(abdi, "AbdiCal");
	cal.book(LocalDate.of(2017, 04, 01), LocalTime.of(16, 00), LocalTime.of(18, 00), "Fika med Ahmed");

	cal.show();
    }
}
