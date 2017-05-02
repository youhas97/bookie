package bookie;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.awt.BorderLayout;

public class CalendarFrame extends JFrame
{
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int MAX_MONTH_DAYS = 31;

    private static final String WINDOW_TITLE = "Bookie";

    private JDialog popUp = null;
    private final JPanel essentialPopUpButtons = new JPanel();
    private final JButton confirm = new JButton("Confirm");
    private final JButton cancel = new JButton("Cancel");
    private final JPanel timeSpanPanel = new JPanel();

    private final JTextField calendarName = new JTextField();
    private JLabel appointmentLabel;
    private JTextField subject = new JTextField("Subject", 7);
    private JTextField newUserName = new JTextField("Name", 7);

    private JComboBox<Integer> days, years, startHour, startMinute, endHour, endMinute;
    private JComboBox<Month> months;
    private JComboBox<User> users = new JComboBox<>();
    private JComboBox<Calendar> userCalendars = new JComboBox<>();

    public CalendarFrame() {
	super(WINDOW_TITLE);
	cancel.addActionListener(new CancelAction());

	final JMenuBar menuBar = new JMenuBar();

	final JMenuItem bookAppointment = new JMenuItem("Book");
	final JMenuItem quit = new JMenuItem("Quit");
	final JMenuItem createCalendar = new JMenuItem("Create calendar");
	final JMenuItem newUser = new JMenuItem("New user");

	appointmentLabel = new JLabel();
	final JScrollPane appointmentScrollPane = new JScrollPane(appointmentLabel);

	final JMenu fileMenu = new JMenu("File");
	final JMenu systemMenu = new JMenu("System");
	final JMenu userMenu = new JMenu("User");

	months = new JComboBox<>();
	days = new JComboBox<>();
	years = new JComboBox<>();
	startHour = new JComboBox<>();
	startMinute = new JComboBox<>();
	endHour = new JComboBox<>();
	endMinute = new JComboBox<>();

	final JPanel startTimePanel = new JPanel();
	startTimePanel.add(new JLabel("Start Time"), BorderLayout.NORTH);
	startTimePanel.add(startHour, BorderLayout.SOUTH);
	startTimePanel.add(startMinute, BorderLayout.SOUTH);

	final JPanel endTimePanel = new JPanel();
	endTimePanel.add(new JLabel("End Time"), BorderLayout.NORTH);

	endTimePanel.add(endHour, BorderLayout.SOUTH);
	endTimePanel.add(endMinute, BorderLayout.SOUTH);

	timeSpanPanel.setLayout(new BorderLayout());
	timeSpanPanel.add(startTimePanel, BorderLayout.WEST);
	timeSpanPanel.add(endTimePanel, BorderLayout.EAST);

	menuBar.add(fileMenu);
	menuBar.add(userMenu);
	menuBar.add(systemMenu);

	fileMenu.add(bookAppointment);
	fileMenu.add(createCalendar);

	systemMenu.add(quit);

	userMenu.add(newUser);

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
		startHour.addItem(time);
		endHour.addItem(time);
	    }
	    startMinute.addItem(time);
	    endMinute.addItem(time);
	}

	for (User user : User.getExistingUsers()) {
	    users.addItem(user);
	}

	bookAppointment.addActionListener(new BookPopupAction());
	createCalendar.addActionListener(new CreateCalendarPopupAction());
	final JButton changeCurrentCalendar = new JButton("Change calendar");
	changeCurrentCalendar.addActionListener(new ChangeCurrentCalendarPopupAction());
	newUser.addActionListener(new NewUserPopupAction());

	quit.addActionListener(new QuitAction());
	this.setLayout(new MigLayout());
	this.add(changeCurrentCalendar, "cell 0 2");
	this.add(appointmentScrollPane, "cell 0 1, w 550::1000, h 50::500, span 2, grow");
	this.add(menuBar, "cell 0 0");

	this.pack();
	this.setLocationRelativeTo(null);
	this.setVisible(true);

	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void showUsersCalendars(User user) {
	for (Calendar cal : user.getCalendars()) {
	    userCalendars.addItem(cal);
	}
    }

    public void showCalendar(Calendar cal) {
	StringBuilder buf = new StringBuilder();
	buf.append("<html>");
	buf.append(cal.getUser());
	buf.append(", ");
	buf.append(cal);
	buf.append("<br>");
	buf.append("-------------");
	buf.append("<br>");
	for (Appointment app : cal.getAppointments()) {
	    buf.append(String.valueOf(app));
	    buf.append("<br>");
	}
	buf.append("<html>");
	appointmentLabel.setText(buf.toString());
    }

    private void createPopUp(ActionListener confirmAction) {
	for (ActionListener listener : confirm.getActionListeners()) {
	    confirm.removeActionListener(listener);
	}

	popUp = new JDialog(this, true);
	popUp.setLayout(new MigLayout());
	essentialPopUpButtons.add(confirm, "west");
	essentialPopUpButtons.add(cancel, "east");
	popUp.add(essentialPopUpButtons, "south");
	confirm.addActionListener(confirmAction);
    }

    final private class NewUserPopupAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    createPopUp(new ConfirmNewUserAction());
	    popUp.add(newUserName);

	    popUp.pack();
	    popUp.setLocationRelativeTo(popUp.getParent());
	    popUp.setVisible(true);
	}
    }

    final private class BookPopupAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    createPopUp(new ConfirmBookAction());
	    popUp.add(days, "cell 0 0");
	    popUp.add(months, "cell 0 0");
	    popUp.add(years, "cell 0 0, gapright unrelated");
	    popUp.add(users);
	    showUsersCalendars((User) users.getSelectedItem());
	    popUp.add(userCalendars);
	    popUp.add(subject);
	    popUp.add(timeSpanPanel, "south");

	    popUp.pack();
	    popUp.setLocationRelativeTo(popUp.getParent());
	    popUp.setVisible(true);
	}
    }

    final private class ConfirmNewUserAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    String name = newUserName.getText();
	    if (!User.userExists(name)) {
		User user = new User(name);
		popUp.dispose();
		System.out.println("\"" + name + "\"" + " created");

	    } else System.out.println(name + " already exits. Try again");
	}
    }

    final private class ConfirmBookAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    TimeSpan span = new TimeSpan(LocalTime.of(startHour.getSelectedIndex(), startMinute.getSelectedIndex()),
					 LocalTime.of(endHour.getSelectedIndex(), endMinute.getSelectedIndex()));
	    ((Calendar) userCalendars.getSelectedItem()).book(LocalDate.of((int) years.getSelectedItem(),
									   ((Month) months.getSelectedItem()).getValue(),
									   (int) days.getSelectedItem()), span,
							      subject.getText());
	    popUp.dispose();
	    showCalendar((Calendar) userCalendars.getSelectedItem());
	}
    }

    final private class ConfirmCreateCalendarAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    String calName = calendarName.getText();
	    Calendar cal = new Calendar((User) users.getSelectedItem(), calName);
	}
    }

    final private class ConfirmChangeCurrentCalendarAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    popUp.dispose();
	    showCalendar((Calendar) userCalendars.getSelectedItem());
	}
    }

    final private class ChangeCurrentCalendarPopupAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    createPopUp(new ConfirmChangeCurrentCalendarAction());
	    popUp.add(users);
	    showUsersCalendars((User) users.getSelectedItem());
	    popUp.add(userCalendars);

	    popUp.pack();
	    popUp.setLocationRelativeTo(popUp.getParent());
	    popUp.setVisible(true);
	}
    }

    final private class CreateCalendarPopupAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    createPopUp(new ConfirmCreateCalendarAction());
	    popUp.add(users, "cell 0 0");
	    popUp.add(calendarName, "cell 4 0, w 200");

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

