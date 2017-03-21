package bookie;

public enum Months {
    JANUARY(31), FEBRUARY(28), MARCH(31), APRIL(30),
    MAY(31), JUNE(30), JULY(31), AUGUST(31), SEPTEMBER(30),
    OCTOBER(31), NOVEMBER(30), DECEMBER(31);

    public final int days;

    Months(final int days) {
	this.days = days;
    }
}
