package jlooch.swing;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The main button for the application. It encapsulates the functionality of the
 * four thread control toggles. Pressing the button will enable the four toggles
 * and play each of the threads at their selected probability. Pressing it again
 * will stop the sound and disable the thread-specific toggles.
 * 
 * @author dan
 *
 */
public class DroneButton extends Button {
	private static final long serialVersionUID = 1L;

	private static final String droneButtonLabelInactive = "drono...";
	private static final String droneButtonLabelActive = "StopEm!";
	private static final Font timesSmallItalic = new Font("Times", Font.ITALIC, 10);
	private static final Color backgroundColor = new Color((float) 0.2, (float) 0.7, (float) 0.8);
	private static final Color foregroundColor = new Color((float) 0.9, (float) 0.1, (float) 0.1);
	private Boolean isActive = false;

	public DroneButton(final List<ThreadToggleButton> toggleButtons) {
		super(droneButtonLabelInactive);

		setLocation(70, 270);
		setSize(70, 20);
		
		setBackground(backgroundColor);
		setForeground(foregroundColor);
		setFont(timesSmallItalic);

		for (ThreadToggleButton toggleButton : toggleButtons) {
			toggleButton.setEnabled(false);
		}

		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				if (!isActive) {
					for (ThreadToggleButton toggleButton : toggleButtons) {
						toggleButton.setEnabled(true);
						toggleButton.startSound();
					}
					setLabel(droneButtonLabelActive);
					isActive = true;
				} else {
					for (ThreadToggleButton toggleButton : toggleButtons) {
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
