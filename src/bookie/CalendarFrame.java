package bookie;

import javafx.scene.input.MouseEvent;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;

public class CalendarFrame extends JFrame
{
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int MAX_MONTH_DAYS = 31;

    private JDialog popUp = null;
    private final JPanel essentialPopUpButtons = new JPanel();
    private final JButton confirm = new JButton("Confirm");
    private final JButton cancel = new JButton("Cancel");
    private final JPanel timeSpanPanel = new JPanel();

    private final JTextField calendarName = new JTextField();
    private final JTextField subject = new JTextField("Subject", 7);
    private JLabel appointmentLabel;

    private JComboBox<Integer> days, years, startHour, startMinute, endHour, endMinute;
    private JComboBox<Month> months;
    private JComboBox<User> users = new JComboBox<>();
    private JComboBox<Calendar> calendars = new JComboBox<>();

    public CalendarFrame() {
	cancel.addActionListener(new CancelAction());

	final JMenuBar menuBar = new JMenuBar();

	final JMenuItem bookAppointment = new JMenuItem("Book");
	final JMenuItem changeUser = new JMenuItem("Change user");
	final JMenuItem quit = new JMenuItem("Quit");
	final JMenuItem createCalendar = new JMenuItem("Create calendar");

	final JLabel currentUser = new JLabel("Current user: Hashem");

	final JPanel infoPanel = new JPanel();

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
		startHour.addItem(time);
		endHour.addItem(time);
	    }
	    startMinute.addItem(time);
	    endMinute.addItem(time);
	}

	for (User user : User.getExistingUsers()) {
	    users.addItem(user);
	}

	bookAppointment.addActionListener(new BookPopup());
	createCalendar.addActionListener(new CreateCalendarPopup());

	quit.addActionListener(new QuitAction());
	this.setLayout(new MigLayout());
	infoPanel.setLayout(new MigLayout());
	this.add(appointmentScrollPane, "cell 0 1, w 550::1000, h 50::500, span 2, grow");
	this.add(menuBar, "cell 0 0");
	this.add(infoPanel, "cell 1 0");

	this.pack();
	this.setLocationRelativeTo(null);
	this.setVisible(true);

	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void showCalendar(Calendar cal) {
	StringBuilder buf = new StringBuilder();
	buf.append("<html>");
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

    final private class BookPopup implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    createPopUp(new ConfirmBook());
	    popUp.add(days, "cell 0 0");
	    popUp.add(months, "cell 0 0");
	    popUp.add(years, "cell 1 0, grow, gapright unrelated");
	    popUp.add(startHour, "cell 0 2");
	    popUp.add(startMinute, "cell 0 2, gapright unrelated");
	    popUp.add(timeSpanPanel, "south");
	    popUp.add(new JLabel("Start Time"), "cell 0 1, gapright unrelated");
	    popUp.add(new JLabel("End Time"), "cell 1 1, span 2");
	    popUp.add(endHour, "cell 1 2");
	    popUp.add(endMinute, "cell 1 2");

	    popUp.add(subject, "cell 2 0");

	    popUp.pack();
	    popUp.setLocationRelativeTo(popUp.getParent());
	    popUp.setVisible(true);
	}
    }

    final private class ConfirmBook implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    TimeSpan span = new TimeSpan(LocalTime.of(startHour.getSelectedIndex(), startMinute.getSelectedIndex()),
					 LocalTime.of(endHour.getSelectedIndex(), endMinute.getSelectedIndex()));
	    //Appointment app = new Appointment( LocalDate.of(years.getSelectedIndex(), months.getSelectedIndex(), days.getSelectedIndex()), span,);
	}
    }

    final private class ConfirmCreateCalendar implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    String calName = calendarName.getText();
	    Calendar cal = new Calendar((User) users.getSelectedItem(), calName);
	    System.out.println("Calendar calendar!");
	}
    }

    final private class CreateCalendarPopup implements ActionListener
    {
	@Override public void actionPerformed(final ActionEvent e) {
	    createPopUp(new ConfirmCreateCalendar());
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

