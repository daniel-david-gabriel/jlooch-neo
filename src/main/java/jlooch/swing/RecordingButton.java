package jlooch.swing;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 * New functionality?! Creates a button which enables sound recording for the
 * droning, synthetic music. Internally, it starts a thread which uses the
 * javax.sound.sampled interface to read the sound being played through the
 * system and save it as a raw .wav. If for some reason this functionality is
 * unavailable, the button will simply be disabled.
 * 
 * @author dan
 *
 */
public class RecordingButton extends Button {
	private static final long serialVersionUID = 1L;

	private static final String recordingButtonLabelInactive = "Rec...";
	private static final String recordingButtonLabelActive = "Enuf!";
	private static final Font timesSmallItalic = new Font("Times", Font.ITALIC, 10);
	private static final Color backgroundColor = new Color((float) 0.2, (float) 0.7, (float) 0.8);
	private static final Color foregroundColor = new Color((float) 0.9, (float) 0.1, (float) 0.1);

	private static final String outFilePath = "looch.wav"; // Externalize this as a parameter?
	private static final AudioFileFormat.Type audioFileType = AudioFileFormat.Type.WAVE;
	private static final Float sampleRate = (float) 44100;
	private static final Integer sampleSizeInBits = 8;
	private static final Integer channels = 2; // Stereo
	private static final AudioFormat audioFormat = new AudioFormat(sampleRate, sampleSizeInBits, channels, true, true);
	private static final DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);

	private Boolean isRecording = false;
	private RecordingThread recordingThread;

	public RecordingButton() {
		super(recordingButtonLabelInactive);

		setLocation(25, 270);
		setSize(40, 20);
		
		setBackground(backgroundColor);
		setForeground(foregroundColor);
		setFont(timesSmallItalic);

		if (!AudioSystem.isLineSupported(dataLineInfo)) {
			disableButton("AudioSystem DataLine is not supported. Disabling Record button!");
		} else {
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (isRecording) {
						stopRecording();
						setLabel(recordingButtonLabelInactive);
						isRecording = false;
					} else {
						startRecording();
						setLabel(recordingButtonLabelActive);
						isRecording = true;
					}
				}
			});
		}
	}

	private void startRecording() {
		if (!isRecording) {
			recordingThread = new RecordingThread();
			recordingThread.start();
		}
	}

	/**
	 * Protected so we can call it from the WindowListener
	 */
	protected void stopRecording() {
		if (isRecording) {
			recordingThread.stopRecording();
		}
	}

	private void disableButton(String message) {
		setEnabled(false);
		System.err.println(message);
	}

	/**
	 * The meat of this class. Using the javax.sound.sampled interface, we get
	 * the data line and open it as an AudioInputStream. We then stream the
	 * audio data to the specified file. When stopRecording() is called, we
	 * close the file.
	 * 
	 * @author dan
	 *
	 */
	private class RecordingThread extends Thread {
		private File outFile;
		private TargetDataLine targetDataLine;

		public void run() {
			try {
				outFile = new File(outFilePath);
				targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);

				targetDataLine.open(audioFormat);
				targetDataLine.start();

				AudioInputStream audioInputStream = new AudioInputStream(targetDataLine);

				AudioSystem.write(audioInputStream, audioFileType, outFile);
			} catch (IOException ioException) {
				disableButton(ioException.getMessage());
			} catch (LineUnavailableException lineUnavailableException) {
				disableButton(lineUnavailableException.getMessage());
			}
		}

		public void stopRecording() {
			targetDataLine.stop();
			targetDataLine.close();
		}
	}
}
