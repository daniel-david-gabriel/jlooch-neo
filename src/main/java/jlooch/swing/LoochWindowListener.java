package jlooch.swing;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * In the event that the user closes the window while the sound is still
 * recording, we will attempt to close the files cleanly before System.exit() is
 * called.
 * 
 * @author dan
 *
 */
public class LoochWindowListener implements WindowListener {

	private final RecordingButton recordingButton;

	public LoochWindowListener(RecordingButton recordingButton) {
		this.recordingButton = recordingButton;
	}

	public void windowClosing(WindowEvent windowEvent) {
		recordingButton.stopRecording();
	}

	public void windowClosed(WindowEvent windowEvent) {}
	public void windowActivated(WindowEvent windowEvent) {}
	public void windowDeactivated(WindowEvent windowEvent) {}
	public void windowDeiconified(WindowEvent windowEvent) {}
	public void windowIconified(WindowEvent windowEvent) {}
	public void windowOpened(WindowEvent windowEvent) {}
}
