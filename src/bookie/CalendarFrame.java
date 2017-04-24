package bookie;

import net.miginfocom.swing.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Month;
import java.time.Year;

public class CalendarFrame extends JFrame
{
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int MAX_MONTH_DAYS = 31;

    private JDialog popUp;
    private final JButton confirm = new JButton("Confirm");
    private final JButton cancel = new JButton("Cancel");

    private final JTextField calendarName = new JTextField(8);

    protected JComboBox<Integer> days, years, hours, minutes;
    protected JComboBox<Month> months;
    protected JComboBox<User> users = new JComboBox<>();

    public CalendarFrame() {

	popUp = new JDialog(this, true);
	popUp.setLayout(new MigLayout("", "[][][][][]", "[][][]"));
	popUp.add(cancel, "south, span 2");
	popUp.add(confirm, "south");

	cancel.addActionListener(new CancelAction());

	final JMenuBar menuBar = new JMenuBar();

	final JMenuItem bookAppointment = new JMenuItem("Book");
	final JMenuItem changeUser = new JMenuItem("Change user");
	final JMenuItem quit = new JMenuItem("Quit");
	final JMenuItem createCalendar = new JMenuItem("Create calendar");

	final JLabel currentUser = new JLabel("Current user: Hashem");

	final JPanel infoPanel = new JPanel();

	final JMenu fileMenu = new JMenu("File");
	final JMenu systemMenu = new JMenu("System");
	final JMenu userMenu = new JMenu("User");


	months = new JComboBox<>();
	days = new JComboBox<>();
	years = new JComboBox<>();
	hours = new JComboBox<>();
	minutes = new JComboBox<>();

	menuBar.add(fileMenu);
	menuBar.add(userMenu);
	menuBar.add(systemMenu);

	fileMenu.add(bookAppointment);
	fileMenu.add(createCalendar);

	systemMenu.add(quit);

	userMenu.add(changeUser);

	infoPanel.add(currentUser, "west");

	for (Month month : Month.values()) {
	    months.addItem(month);
	}

	for (int day = 1; day <= MAX_MONTH_DAYS; day++) {
	    days.addItem(day);
	}

	for (int year = Year.now().getValue(); year <= Year.now().getValue() + 2; year++) {
	    years.addItem(year);
	}

	for (int time = 0; time < MINUTES_PER_HOUR; time++) {
	    if (time < HOURS_PER_DAY) {
		hours.addItem(time);
	    }
	    minutes.addItem(time);
	}

	for (User user : User.getExistingUsers()) {
	    users.addItem(user);
	}

	bookAppointment.addActionListener(new BookAction());
	createCalendar.addActionListener(new CreateCalendarAction());

	quit.addActionListener(new QuitAction());
	this.setLayout(new MigLayout());
	infoPanel.setLayout(new MigLayout());
	this.add(menuBar, "west");
	this.add(infoPanel, "south");

	this.pack();
	this.setLocationRelativeTo(null);
	this.setVisible(true);

	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    final private class BookAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    popUp.add(days, "north, west");
	    popUp.add(months, "north, west");
	    popUp.add(years, "north, west ,gapright unrelated");
	    popUp.add(hours, "north, west");
	    popUp.add(minutes, "north, west, wrap");

	    popUp.pack();
	    popUp.setLocationRelativeTo(popUp.getParent());
	    popUp.setVisible(true);
	}
    }

    final private class CreateCalendarAction implements ActionListener
    {

	@Override public void actionPerformed(final ActionEvent e) {
	    popUp.add(users);
	    popUp.add(calendarName);
	    popUp.pack();
	    popUp.setLocationRelativeTo(popUp.getParent());
	    popUp.setVisible(true);
	}
    }

    final private class QuitAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    if (JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "WARNING", JOptionPane.YES_NO_OPTION) ==
		JOptionPane.YES_OPTION) {
		// yes option

		System.exit(0);
	    }
	}
    }

    final private class CancelAction implements ActionListener
    {

	@Override public void actionPerformed(final ActionEvent e) {
	    if (JOptionPane.showConfirmDialog(cancel, "Are you sure you want to cancel?", "WARNING",
					      JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		// yes option
		popUp.dispose();
	    }
	}
    }
}

