package bookie;

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

    private JMenuItem quit;
    private JLabel currentUser;
    private JPanel infoPanel;
    private JMenuItem bookAppointment;
    private JMenuBar menuBar;
    private JMenu fileMenu, systemMenu;
    private JFrame popUpFrame;

    private JComboBox<Integer> days, years, hours, minutes;
    private JComboBox<User> users;
    private JComboBox<Month> months;

    public CalendarFrame() {

	quit = new JMenuItem("Quit");
	bookAppointment = new JMenuItem("Book");
	currentUser = new JLabel("Current user: Hashem²");
	infoPanel = new JPanel();
	menuBar = new JMenuBar();
	fileMenu = new JMenu("File");
	systemMenu = new JMenu("System");

	menuBar.add(fileMenu);
	menuBar.add(systemMenu);
	fileMenu.add(bookAppointment);
	systemMenu.add(quit);
	infoPanel.add(currentUser, BorderLayout.WEST);

	for (Month month : Month.values()) {
	    months.addItem(month);
	}

	for (int day = 1; day <= 31; day++) {
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

	bookAppointment.addActionListener(new ActionListener()
	{
	    // Creates a new actionlistener on the spot instead of implementing the whole interface.
	    @Override public void actionPerformed(final ActionEvent e) {

	    }
	});

	quit.addActionListener(new ActionListener()
	{
	    // Creates a new actionlistener on the spot instead of implementing the whole interface.
	    @Override public void actionPerformed(final ActionEvent e) {
		if (JOptionPane
			    .showConfirmDialog(quit, "Are you sure you want to quit?", "WARNING", JOptionPane.YES_NO_OPTION) ==
		    JOptionPane.YES_OPTION) {
		    // yes option
		    System.exit(0);
		}
	    }
	});

	this.setLayout(new BorderLayout());
	infoPanel.setLayout(new BorderLayout());
	this.add(menuBar, BorderLayout.NORTH);
	this.add(infoPanel, BorderLayout.SOUTH);

	this.pack();
	this.setVisible(true);

	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
