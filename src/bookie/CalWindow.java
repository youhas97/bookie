package bookie;

import javax.swing.*;
import java.awt.*;

public class CalWindow extends JFrame
{
    private JFrame cal;
    private JButton quit;
    private JLabel currentUser;
    private JPanel infoPanel;
    private JPanel controlPanel;

    public CalWindow() {
        cal = new JFrame();
	quit = new JButton("Quit");
	currentUser = new JLabel("Can");
	infoPanel = new JPanel();
	controlPanel = new JPanel();

	this.setLayout(new BorderLayout());

        this.add(cal, BorderLayout.CENTER);
        infoPanel.add(currentUser);

    }
}
