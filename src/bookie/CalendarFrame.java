package bookie;

import net.miginfocom.swing.MigLayout;

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

    private JDialog popUp = null;
    private final JButton confirm = new JButton("Confirm");
    private final JButton cancel = new JButton("Cancel");

    private final JTextField calendarName = new JTextField(10);

    protected JComboBox<Integer> days, years, hours, minutes;
    protected JComboBox<Month> months;
    protected JComboBox<User> users = new JComboBox<>();

    public CalendarFrame() {
	cancel.addActionListener(new CancelAction());

	final JMenuBar menuBar = new JMenuBar();

	final JMenuItem bookAppointment = new JMenuItem("Book");
	final JMenuItem changeUser = new JMenuItem("Change user");
	final JMenuItem quit = new JMenuItem("Quit");
	final JMenuItem createCalendar = new JMenuItem("Create calendar");

	final JLabel currentUser = new JLabel("Current user: Hashem");

	final JPanel infoPanel = new JPanel();

	final JScrollPane currentCalendar = new JScrollPane();

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

	bookAppointment.addActionListener(new BookPopup());
	createCalendar.addActionListener(new CreateCalendarPopup());

	quit.addActionListener(new QuitAction());
	this.setLayout(new MigLayout());
	infoPanel.setLayout(new MigLayout());
	this.add(currentCalendar, "center");
	this.add(menuBar, "west");
	this.add(infoPanel, "south");

	this.pack();
	this.setLocationRelativeTo(null);
	this.setVisible(true);

	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void createPopUp() {
	for (ActionListener listener : confirm.getActionListeners()) {
	    confirm.removeActionListener(listener);
	}

	popUp = new JDialog(this, true);
	popUp.setLayout(new MigLayout());
	popUp.add(confirm, "cell 0 5 1 5");
	popUp.add(cancel, "cell 4 5 1 5, grow");
    }

    final private class BookPopup implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    createPopUp();
	    popUp.add(days, "cell 0 0");
	    popUp.add(months, "cell 0 0");
	    popUp.add(years, "cell 0 0, gapright unrelated");
	    popUp.add(hours, "cell 4 0");
	    popUp.add(minutes, "cell 4 0");

	    popUp.pack();
	    popUp.setLocationRelativeTo(popUp.getParent());
	    popUp.setVisible(true);
	}
    }

    final private class ConfirmCreateCalendar implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    String calName = calendarName.getText();
	    Calendar cal = new Calendar((User) users.getSelectedItem(), calName);
	    System.out.println(cal);
	}
    }

    final private class CreateCalendarPopup implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    createPopUp();
	    popUp.add(users, "gapright unrelated");
	    popUp.add(calendarName);

	    popUp.pack();
	    popUp.setLocationRelativeTo(popUp.getParent());
	    popUp.setVisible(true);
	    confirm.addActionListener(new ConfirmCreateCalendar());
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

