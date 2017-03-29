package bookie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Month;

public class CalendarFrame extends JFrame
{
    private JMenuItem quit;
    private JLabel currentUser;
    private JPanel infoPanel;
    private JMenuItem bookAppointment;
    private JMenuBar menuBar;
    private JMenu fileMenu, systemMenu;
    private JFrame popUpFrame;

    private JComboBox<Integer> days, startTimes, endTimes, years;
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
