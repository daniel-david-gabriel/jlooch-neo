package jlooch.swing;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jlooch.SoundThread;

public class ThreadToggleButton extends Button {
	private static final long serialVersionUID = 1L;
	
	private static final Dimension buttonDimension = new Dimension(10, 10);
	
	private static final String enabledLabelText = "+";
	private static final String disabledLabelText = "-";
	
	private static final Color goColor = new Color((float)0.9, (float)0.1, (float)0.2);
	private static final Color stopColor = new Color((float)0.1, (float)0.8, (float)0.7);
	
	private Boolean shouldPlay = true;
	private SoundThread soundThread;

	public ThreadToggleButton(final SoundThread soundThread, final Point location) {
		super(enabledLabelText);
		
		setSize(buttonDimension);
		setLocation(location);
		setBackground(goColor);
		setForeground(Color.yellow);
		
		this.soundThread = soundThread;
		
		ActionListener toggleActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				if (shouldPlay) {
					setLabel(disabledLabelText);
					setBackground(stopColor);
					setForeground(Color.white);
					shouldPlay = false;
					
					stopSound();
				}
				else {
					setLabel(enabledLabelText);
					setBackground(goColor);
					setForeground(Color.yellow);
					shouldPlay = true;
					
					startSound();
				}
			}
		};
		
		addActionListener(toggleActionListener);
	}

	public void startSound() {
		if (shouldPlay && !soundThread.isRunning()) {
			soundThread.start();						
		}
	}
	
	public void stopSound() {
		if (soundThread.isRunning()) {
			soundThread.stopSound();
		}
	}
}
