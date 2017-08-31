package se.liu.ida.dx17.tddd78.bookie.GUI;

import net.miginfocom.swing.MigLayout;
import se.liu.ida.dx17.tddd78.bookie.Calendar.Appointment;
import se.liu.ida.dx17.tddd78.bookie.Calendar.Calendar;
import se.liu.ida.dx17.tddd78.bookie.Calendar.TimeSpan;
import se.liu.ida.dx17.tddd78.bookie.User.User;
import se.liu.ida.dx17.tddd78.bookie.User.UserList;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.awt.BorderLayout;

/**
 * User interface for the program.
 * Graphical representation of the program.
 * Handles everything.
 */
public class CalendarFrame extends JFrame
{
    private UserList userList = UserList.getInstance();

    /*
    Constants that do not depend on the object
     */
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int MAX_MONTH_DAYS = 31;
    private static final String WINDOW_TITLE = "Bookie";
    private static final int TEXTFIELDSIZE = 12;

    private JDialog popUp = null;
    private final JPanel essentialPopUpButtons = new JPanel();
    private final JButton confirm = new JButton("Confirm");
    private final JButton cancel = new JButton("Cancel");
    private final JPanel timeSpanPanel = new JPanel();

    private final JTextField calendarName = new JTextField("Name");
    private JLabel appointmentLabel;
    private JTextField subject = new JTextField("Enter a subject!", TEXTFIELDSIZE);
    private JTextField newUserName = new JTextField("Enter name!", TEXTFIELDSIZE);
    private JPasswordField userPassword = new JPasswordField(TEXTFIELDSIZE);

    private JCheckBox userPasswordToggle = new JCheckBox("Password");

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
	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

	userLabel = new JLabel("Current user: none");
	appointmentLabel = new JLabel();

	months = new JComboBox<>();
	days = new JComboBox<>();
	years = new JComboBox<>();
	startHour = new JComboBox<>();
	startMinute = new JComboBox<>();
	endHour = new JComboBox<>();
	endMinute = new JComboBox<>();
    }

    public void run() {
	final JMenuBar menuBar = new JMenuBar();

	final JMenuItem bookAppointment = new JMenuItem("Book");
	final JMenuItem quit = new JMenuItem("Quit");
	final JMenuItem createCalendar = new JMenuItem("Create calendar");
	final JMenuItem newUser = new JMenuItem("New user");
	final JMenuItem changeUser = new JMenuItem("Change user");
	final JMenuItem cancelAppointment = new JMenuItem("Cancel appointment");
	final JMenuItem removeUser = new JMenuItem("Remove current user");

	final JButton deleteCalendar = new JButton("Delete calendar");
	final JButton changeDisplayCalendar = new JButton("Select calendar");

	final JScrollPane appointmentScrollPane = new JScrollPane(appointmentLabel);

	final JMenu fileMenu = new JMenu("File");
	final JMenu systemMenu = new JMenu("System");
	final JMenu userMenu = new JMenu("User");

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
	userMenu.add(removeUser);

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

	showCalendar();
	updateUsers();

	changeDisplayCalendar.addActionListener(new SelectCalendarPopupAction());
	deleteCalendar.addActionListener(new DeleteCalendarAction());
	cancel.addActionListener(new CancelAction());
	bookAppointment.addActionListener(new BookPopupAction());
	createCalendar.addActionListener(new CreateCalendarPopupAction());
	newUser.addActionListener(new NewUserPopupAction());
	changeUser.addActionListener(new ChangeCurrentUserAction());
	removeUser.addActionListener(new DeleteUserAction());
	cancelAppointment.addActionListener(new CancelAppointmentPopupAction());

	newUserName.addMouseListener(new ClearNewUserName());
	calendarName.addMouseListener(new ClearCalendarName());
	subject.addMouseListener(new ClearSubject());

	quit.addActionListener(new QuitAction());
	this.setLayout(new MigLayout());
	this.add(changeDisplayCalendar, "cell 0 2");
	this.add(deleteCalendar, "cell 1 2");
	this.add(appointmentScrollPane, "cell 0 1, w 550::1000, h 50::500, span 2, grow");
	this.add(menuBar, "cell 0 0");
	this.add(userLabel, "cell 1 0");

	this.pack();
	this.setLocationRelativeTo(null);
	this.setVisible(true);

	closeProgram(this);
    }

    private void closeProgram(JFrame frame) {
	addWindowListener(new WindowAdapter()
	{
	    @Override public void windowClosing(WindowEvent windowEvent) {
		if (JOptionPane.showConfirmDialog(frame, "Are you sure to close this window?", "Really Closing?",
						  JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) ==
		    JOptionPane.YES_OPTION) {
		    System.exit(0);
		}
	    }
	});
    }

    final private class ClearNewUserName extends MouseAdapter
    {
	@Override public void mouseClicked(MouseEvent e) {
	    newUserName.setText("");
	}
    }

    final private class ClearCalendarName extends MouseAdapter
    {
	@Override public void mouseClicked(MouseEvent e) {
	    calendarName.setText("");
	}
    }

    final private class ClearSubject extends MouseAdapter
    {
	@Override public void mouseClicked(MouseEvent e) {
	    subject.setText("");
	}
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

    private void updateCurrentUserCalendars() {
	userCalendars.removeAllItems();
	for (Calendar cal : currentUser.getCalendars()) {
	    userCalendars.addItem(cal);
	}
    }

    public void updateAppointments() {
	if (currentCal != null) {
		appointments.removeAllItems();
		for (Appointment app : currentCal.getAppointments()) {
		    appointments.addItem(app);
		}
	}
    }

    public void showCalendar() {
	if (currentCal != null) {
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
	} else appointmentLabel.setText("No calendar selected!");
    }

    public void showPopUp() {
	popUp.pack();
	popUp.setLocationRelativeTo(popUp.getParent());
	popUp.setVisible(true);
    }

    private void createPopUp(ActionListener action, String title) {
	for (ActionListener listener : confirm.getActionListeners()) {
	    confirm.removeActionListener(listener);
	}

	popUp = new JDialog(this, true);
	popUp.setTitle(title);
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

    private void showMessage(String text) {
	JOptionPane.showOptionDialog(confirm, text, "Error", JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null,
				     options, options[0]);
    }

    final private class DeleteCalendarAction implements ActionListener
    {

	@Override public void actionPerformed(final ActionEvent e) {
	    if (currentCal != null) {
		createPopUp(new ConfirmDeleteCalendarAction(), "Delete calendar");
		showPopUp();
	    } else {
		showMessage("No calendar selected!");
	    }
	}
    }

    final private class ConfirmDeleteCalendarAction implements ActionListener
    {

	@Override public void actionPerformed(final ActionEvent e) {
	    currentUser.deleteCalendar(currentCal);
	    currentCal = null;
	    showCalendar();
	    popUp.dispose();
	}
    }

    final private class DeleteUserAction implements ActionListener
    {

	@Override public void actionPerformed(final ActionEvent e) {
	    if (currentUser != null) {
		createPopUp(new ConfirmDeleteUserAction(), "Delete User");
		popUp.add(new JLabel("Delete user: " + currentUser.getName() + "?"));
		showPopUp();
	    } else {
		showMessage("No user selected!");
	    }
	}
    }

    final private class ConfirmDeleteUserAction implements ActionListener
    {

	@Override public void actionPerformed(final ActionEvent e) {
	    userList.deleteUser(currentUser);
	    currentUser = null;
	    currentCal = null;
	    showCalendar();
	    updateCurrentUserLabel();
	    popUp.dispose();
	}
    }

    final private class ChangeCurrentUserAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    updateUsers();
	    if (users.getItemCount() != 0) {
		createPopUp(new ConfirmChangeCurrentUserAction(), "Change user");
		popUp.add(users, "span 2");
		popUp.add(userPassword);
		userPassword.setText("");
		userPassword.setVisible(true);
		showPopUp();

	    } else {
		showMessage("No existing users!");
	    }
	}
    }

    final private class ConfirmChangeCurrentUserAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    if (((User) users.getSelectedItem()).getPassword().equals(new String(userPassword.getPassword()))) {
		currentUser = (User) users.getSelectedItem();
		updateCurrentUserLabel();
		popUp.dispose();
		showMessage("User has been changed.");
	    } else {
		showMessage("Incorrect password, try again");
	    }
	}
    }

    final private class CancelAppointmentPopupAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    updateAppointments();
	    if (currentCal != null) {
		if (appointments.getItemCount() != 0) {
		    createPopUp(new ConfirmCancelAppointmentAction(), "Cancel appointment");
		    updateAppointments();
		    popUp.add(appointments);
		    showPopUp();
		} else {
		    showMessage("User does not have any appointments");
		}
	    } else {
		showMessage("No calendar selected!");
	    }
	}
    }

    final private class NewUserPopupAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    createPopUp(new ConfirmNewUserAction(), "Create user");
	    popUp.add(newUserName);
	    newUserName.setText("Enter name");
	    popUp.add(userPasswordToggle);
	    userPasswordToggle.setSelected(false);
	    popUp.add(userPassword);
	    userPassword.setVisible(false);

	    userPasswordToggle.addActionListener(new ActionListener()
	    {
		public void actionPerformed(ActionEvent event) {
		    userPassword.setVisible(userPasswordToggle.isSelected());
		    userPassword.setText("");
		    invalidate();
		    validate();
		}
	    });
	    showPopUp();
	}
    }

    final private class BookPopupAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    if (currentCal != null) {
		createPopUp(new ConfirmBookAction(), "Book");
		popUp.add(days, "cell 0 0");
		popUp.add(months, "cell 0 0");
		popUp.add(years, "cell 0 0, gapright unrelated");
		popUp.add(subject);
		subject.setText("Enter a subject!");
		popUp.add(timeSpanPanel, "south");

		showPopUp();
	    } else {
		showMessage("No calendar selected!");
	    }

	}
    }

    final private class ConfirmCancelAppointmentAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
		currentCal.cancelAppointment(((Appointment) appointments.getSelectedItem()));
		popUp.dispose();
		showCalendar();
		showMessage(appointments.getSelectedItem() + " has been canceled");
	}
    }


    final private class ConfirmNewUserAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    try {
		if (!newUserName.getText().equals("Enter name")) {
		    if (new String(userPassword.getPassword()).isEmpty()) {

			final User user = new User(newUserName.getText());
			if (!UserList.userExists(user)) {
			    UserList.addUser(user);
			    popUp.dispose();
			    showMessage("New user \"" + newUserName.getText() + "\"" + " created!");
			}

		    } else {

			final User user = new User(newUserName.getText(), new String(userPassword.getPassword()));
			if (!UserList.userExists(user)) {
			    UserList.addUser(user);
			    popUp.dispose();
			    showMessage("New user \"" + newUserName.getText() + "\"" + " created!");
			}
		    }
		} else showMessage("Please enter a name!");

	    } catch (UnsupportedOperationException | IllegalArgumentException exception) {
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

		LocalDate date = LocalDate.of((int) years.getSelectedItem(), ((Month) months.getSelectedItem()).getValue(), (int) days.getSelectedItem());
		if (subject.getText().equals("Enter a subject!")) {
		    showMessage("Please enter a subject!");
		} else {
		    currentCal.book(date, span, subject.getText());
		    popUp.dispose();
		}
	    } catch (IllegalArgumentException | DateTimeException exception) {
		showErrorDialog(exception);
	    }
	    showCalendar();
	}
    }

    final private class ConfirmCreateCalendarAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    try {
		if (!calendarName.getText().isEmpty() && !calendarName.getText().equals("Calendar name")) {
		    final Calendar cal = new Calendar(currentUser, calendarName.getText());
		    if (!(currentUser.getCalendars().contains(cal))) {
			currentUser.addCalendar(cal);
			showMessage(calendarName.getText() + " successfully created");
			popUp.dispose();
		    } //else {
		    //   showMessage("A calendar with this name already exists");
		    // }

		} else {
		    showMessage("Enter a calendar name");
		}
	    } catch (IllegalArgumentException exception) {
		showErrorDialog(exception);
	    }
	}
    }

    final private class ConfirmSelectCalendarAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
		popUp.dispose();
		currentCal = (Calendar) userCalendars.getSelectedItem();
		showCalendar();
	}
    }

    final private class SelectCalendarPopupAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {

	    if (currentUser != null) {
		updateCurrentUserCalendars();
		if (userCalendars.getItemCount() != 0) {
		    createPopUp(new ConfirmSelectCalendarAction(), "Select calendar");
		    popUp.add(userCalendars);

		    showPopUp();
		} else {
		    showMessage("No existing calendars!");
		}
	    } else {
		showMessage("No user selected!");
	    }

	}
    }

    final private class CreateCalendarPopupAction implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    if (currentUser != null) {
		createPopUp(new ConfirmCreateCalendarAction(), "Create calendar");
		popUp.add(calendarName, "cell 0 0, w 200, span 4");
		calendarName.setText("Calendar name");
		showPopUp();
	    } else showMessage("No user is selected!");
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
	    popUp.dispose();
	}
    }
}
