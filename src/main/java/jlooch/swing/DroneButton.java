package jlooch.swing;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DroneButton extends Button {
	private static final long serialVersionUID = 1L;

	private static final String droneButtonLabelInactive = "drono...";
	private static final String droneButtonLabelActive = "StopEm!";
	private static final Font timesSmallItalic = new Font("Times", Font.ITALIC, 10);
	private static final Color backgroundColor = new Color((float)0.2, (float)0.7, (float)0.8);
	private static final Color foregroundColor = new Color((float)0.9, (float)0.1, (float)0.1);
	
	private Boolean isActive = false;

	public DroneButton(final List<ThreadToggleButton> toggleButtons) {
		super(droneButtonLabelInactive);
		
		setBackground(backgroundColor);
		setForeground(foregroundColor);
		setLocation(65, 270);
		setSize(70, 20);
		setFont(timesSmallItalic);
		
		for(ThreadToggleButton toggleButton : toggleButtons) {
			toggleButton.setEnabled(false);
		}
		
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!isActive) {
					for(ThreadToggleButton toggleButton : toggleButtons) {
						toggleButton.setEnabled(true);
						toggleButton.startSound();
					}
					setLabel(droneButtonLabelActive);
					isActive = true;
				}
				else {
					for(ThreadToggleButton toggleButton : toggleButtons) {
						toggleButton.setEnabled(false);
						toggleButton.stopSound();
					}
					setLabel(droneButtonLabelInactive);
					isActive = false;
				}
			}
		});
	}
}
