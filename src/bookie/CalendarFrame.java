package bookie;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.awt.BorderLayout;

public class CalendarFrame extends JFrame
{
    private UserList userList = UserList.getInstance();

    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int MAX_MONTH_DAYS = 31;

    private static final String WINDOW_TITLE = "Bookie";

    private JDialog popUp = null;
    private final JPanel essentialPopUpButtons = new JPanel();
    private final JButton confirm = new JButton("Confirm");
    private final JButton cancel = new JButton("Cancel");
    private final JPanel timeSpanPanel = new JPanel();
    private final JButton changeDisplayCalendar = new JButton("Change calendar");

    private final JTextField calendarName = new JTextField();
    private JLabel appointmentLabel;
    private JTextField subject = new JTextField("Subject", 7);
    private JTextField newUserName = new JTextField("Name", 7);

    private JComboBox<Integer> days, years, startHour, startMinute, endHour, endMinute;
    private JComboBox<Month> months;
    private JComboBox<User> users = new JComboBox<>();
    private JComboBox<Calendar> userCalendars = new JComboBox<>();
    private Object[] options = { "OK" };
    private JComboBox<Appointment> appointments = new JComboBox<>();
    private Calendar currentCal = null;
    private User currentUser = null;
    private JLabel userLabel;

    public CalendarFrame() {
	super(WINDOW_TITLE);
	cancel.addActionListener(new CancelAction());

	userLabel = new JLabel("Current user: none");

	final JMenuBar menuBar = new JMenuBar();

	final JMenuItem bookAppointment = new JMenuItem("Book");
	final JMenuItem quit = new JMenuItem("Quit");
	final JMenuItem createCalendar = new JMenuItem("Create calendar");
	final JMenuItem newUser = new JMenuItem("New user");
	final JMenuItem changeUser = new JMenuItem("Change user");
	final JMenuItem cancelAppointment = new JMenuItem("Cancel appointment");

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
	fileMenu.add(cancelAppointment);
	fileMenu.add(createCalendar);

	systemMenu.add(quit);

	userMenu.add(newUser);
	userMenu.add(changeUser);

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

	updateUsers();

	final JButton changeDisplayCalendar = new JButton("Change calendar");
	changeDisplayCalendar.addActionListener(new ChangeCurrentCalendarPopupAction());
	bookAppointment.addActionListener(new BookPopupAction());
	createCalendar.addActionListener(new CreateCalendarPopupAction());
	final JButton changeCurrentCalendar = new JButton("Change calendar");
	changeCurrentCalendar.addActionListener(new ChangeCurrentCalendarPopupAction());
	newUser.addActionListener(new NewUserPopupAction());
	changeUser.addActionListener(new ChangeCurrentUserAction());
	cancelAppointment.addActionListener(new CancelAppointmentPopupAction());

	quit.addActionListener(new QuitAction());
	this.setLayout(new MigLayout());
	this.add(changeDisplayCalendar, "cell 0 2");
	this.add(appointmentScrollPane, "cell 0 1, w 550::1000, h 50::500, span 2, grow");
	this.add(menuBar, "cell 0 0");
	this.add(userLabel, "cell 1 0");

	this.pack();
	this.setLocationRelativeTo(null);
	this.setVisible(true);

	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void updateCurrentUserLabel() {
	String currentUserLabel;
	if (currentUser != null) {
	    currentUserLabel = currentUser.toString();
	} else {
	    currentUserLabel = "none";
	}
	userLabel.setText("Current user: " + currentUserLabel);
    }

    private void updateUsers() {
	users.removeAllItems();
	for (User user : userList.getExistingUsers()) {
	    users.addItem(user);
	}
    }

    private void showCurrentUserCalendars() {
	userCalendars.removeAllItems();
	for (Calendar cal : currentUser.getCalendars()) {
	    userCalendars.addItem(cal);
	}
    }

    public void showAppointments() {
	try {
	    appointments.removeAllItems();
	    for (Appointment app : currentCal.getAppointments()) {
		appointments.addItem(app);
	    }
	} catch (IllegalArgumentException e) {
	    showErrorDialog(e);
	}
    }

    public void showCalendar() {
	StringBuilder buf = new StringBuilder();
	buf.append("<html>");
	buf.append(currentCal.getUser());
	buf.append(", ");
	buf.append(currentCal);
	buf.append("<br>");
	buf.append("-------------");
	buf.append("<br>");
	for (Appointment app : currentCal.getAppointments()) {
	    buf.append(String.valueOf(app));
	    buf.append("<br>");
	}
	buf.append("<html>");
	appointmentLabel.setText(buf.toString());
    }

    public void showPopUp() {
	popUp.pack();
	popUp.setLocationRelativeTo(popUp.getParent());
	popUp.setVisible(true);
    }

    private void createPopUp(ActionListener action) {
	for (ActionListener listener : confirm.getActionListeners()) {
	    confirm.removeActionListener(listener);
	}

	popUp = new JDialog(this, true);
	popUp.setLayout(new MigLayout());
	essentialPopUpButtons.add(confirm, "west");
	essentialPopUpButtons.add(cancel, "east");
	popUp.add(essentialPopUpButtons, "south");
	confirm.addActionListener(action);
    }

    private void showErrorDialog(Exception e) {
	JOptionPane.showOptionDialog(confirm, e.getMessage(), "Error", JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE,
				     null, options, options[0]);
    }

    final private class ChangeCurrentUserAction implements ActionListener
    {

	@Override public void actionPerformed(final ActionEvent e) {
	    createPopUp(new ConfirmChangeCurrentUserAction());
	    updateUsers();
	    popUp.add(users, "span 2");
	    showPopUp();
	}
    }

    final private class ConfirmChangeCurrentUserAction implements ActionListener
    {

	@Override public void actionPerformed(final ActionEvent e) {
	    currentUser = (User) users.getSelectedItem();
	    updateCurrentUserLabel();
	    popUp.dispose();
	}
    }

    final private class CancelAppointmentPopupAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    createPopUp(new ConfirmCancelAppointmentAction());
	    if (currentCal != null) {
		showAppointments();
		popUp.add(appointments);
		showPopUp();
	    } else {
		JOptionPane.showOptionDialog(confirm, "No calendar selected!", "Error", JOptionPane.PLAIN_MESSAGE,
					     JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	    }
	}
    }


    final private class NewUserPopupAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    createPopUp(new ConfirmNewUserAction());
	    popUp.add(newUserName);

	    showPopUp();
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
	    showCurrentUserCalendars();
	    popUp.add(userCalendars);
	    popUp.add(subject);
	    popUp.add(timeSpanPanel, "south");

	    showPopUp();
	}
    }

    final private class ConfirmCancelAppointmentAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    currentCal.cancelAppointment(((Appointment) appointments.getSelectedItem()));
	    popUp.dispose();
	    showCalendar();
	}
    }


    final private class ConfirmNewUserAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    String name = newUserName.getText();
	    try {
		User user = new User(name);
		popUp.dispose();
		JOptionPane
			.showOptionDialog(confirm, "New user \"" + name + "\"" + " created", "Error", JOptionPane.PLAIN_MESSAGE,
					  JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	    } catch (UnsupportedOperationException exception) {
		showErrorDialog(exception);
	    }
	}
    }

    final private class ConfirmBookAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    try {
		TimeSpan span = new TimeSpan(LocalTime.of(startHour.getSelectedIndex(), startMinute.getSelectedIndex()),
					     LocalTime.of(endHour.getSelectedIndex(), endMinute.getSelectedIndex()));

		LocalDate date = LocalDate.of((int) years.getSelectedItem(), ((Month) months.getSelectedItem()).getValue(),
					      (int) days.getSelectedItem());

		((Calendar) userCalendars.getSelectedItem()).book(date, span, subject.getText());
	    } catch (IllegalArgumentException | DateTimeException exception) {
		showErrorDialog(exception);
	    }
	    popUp.dispose();
	    currentCal = (Calendar) userCalendars.getSelectedItem();
	    showCalendar();
	}
    }

    final private class ConfirmCreateCalendarAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    String calName = calendarName.getText();
	    try {
		Calendar cal = new Calendar((User) users.getSelectedItem(), calName);
		JOptionPane.showOptionDialog(confirm, "Calendar successfully created", "Error", JOptionPane.PLAIN_MESSAGE,
					     JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		popUp.dispose();
	    } catch (IllegalArgumentException exception) {
		showErrorDialog(exception);
	    }
	    popUp.dispose();
	}
    }

    final private class ConfirmChangeCurrentCalendarAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    popUp.dispose();
	    currentCal = (Calendar) userCalendars.getSelectedItem();
	    showCalendar();
	}
    }

    final private class ChangeCurrentCalendarPopupAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    if (currentUser != null) {
		createPopUp(new ConfirmChangeCurrentCalendarAction());
		showCurrentUserCalendars();
		popUp.add(userCalendars);

		showPopUp();
	    } else {
		JOptionPane.showOptionDialog(confirm, "No user selected!", "Error", JOptionPane.PLAIN_MESSAGE,
					     JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	    }
	}
    }

    final private class CreateCalendarPopupAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    createPopUp(new ConfirmCreateCalendarAction());
	    popUp.add(users, "cell 0 0");
	    popUp.add(calendarName, "cell 4 0, w 200");

	    showPopUp();
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

