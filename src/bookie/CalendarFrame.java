package bookie;

import net.miginfocom.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Month;
import java.time.Year;

public class CalendarFrame extends JFrame
{
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int MAX_MONTH_DAYS = 31;

    private JMenuItem quit;

    private JComboBox<Integer> days, years, hours, minutes;
    private JComboBox<Month> months;

    public CalendarFrame() {
	/*
        A template for popup windows
         */

	quit = new JMenuItem("Quit");
	final JMenuItem bookAppointment = new JMenuItem("Book");
	final JMenuItem changeUser = new JMenuItem("Change user");
	final JLabel currentUser = new JLabel("Current user: Hashem");
	final JPanel infoPanel = new JPanel();
	final JMenuBar menuBar = new JMenuBar();
	final JMenu fileMenu = new JMenu("File");
	final JMenu systemMenu = new JMenu("System");

	months = new JComboBox<>();
	final JComboBox<User> users = new JComboBox<>();
	days = new JComboBox<>();
	years = new JComboBox<>();
	hours = new JComboBox<>();
	minutes = new JComboBox<>();

	menuBar.add(fileMenu);
	menuBar.add(systemMenu);
	fileMenu.add(bookAppointment);
	fileMenu.add(changeUser);
	systemMenu.add(quit);
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

	bookAppointment.addActionListener(new PopUpAction());

	quit.addActionListener(new QuitAction());
	this.setLayout(new MigLayout());
	infoPanel.setLayout(new MigLayout());
	this.add(menuBar, "west");
	this.add(infoPanel, "south");

	this.pack();
	this.setVisible(true);

	//Positions the frame in the middle of the monitor
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

	setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    final private class PopUpAction implements ActionListener
    {
	//if source is book.
	@Override public void actionPerformed(final ActionEvent e) {
	    final JFrame popUpFrame = new JFrame();
	    popUpFrame.setLayout(new MigLayout());
	    popUpFrame.add(days);
	    popUpFrame.add(months);
	    popUpFrame.add(years);
	    popUpFrame.add(hours);
	    popUpFrame.add(minutes);
	    popUpFrame.setVisible(true);
	    popUpFrame.pack();
	}
	//if source is change user {...}
    }

    final private class QuitAction implements ActionListener
    {

	@Override public void actionPerformed(final ActionEvent e) {
	    if (JOptionPane.showConfirmDialog(quit, "Are you sure you want to quit?", "WARNING", JOptionPane.YES_NO_OPTION) ==
		JOptionPane.YES_OPTION) {
		// yes option
		System.exit(0);
	    }
	}
    }
}
