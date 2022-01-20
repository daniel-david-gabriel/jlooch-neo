package jlooch.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.softsynth.jsyn.Synth;
import com.softsynth.jsyn.SynthAlert;
import com.softsynth.jsyn.SynthException;

import jlooch.BurstNote;
import jlooch.DroneNotes;
import jlooch.SeqNotes;
import jlooch.Speckle;
import jlooch.WarbleNote;

/**
 * Designed to faithfully recreate the original UI as closely as possible, it's
 * a stretch to call this "Swing" anything. Almost all elements contained in
 * this are raw AWT components, but the parent container is a JFrame. In
 * addition to the original functionality, there is also a record button which
 * allows the user to record the sounds generated to a raw .wav file.
 * 
 * @author dan
 *
 */
public class SwingLooch extends JFrame {
	private static final long serialVersionUID = 1L;

	// Window constants
	private static final Dimension windowDimension = new Dimension(210, 350);
	private static final String frameTitle = "bark!  bark!";
	private static final String iconPath = "src/main/resources/loochicon.gif";

	// Control constants
	private static final String controlLabelText = "Spirit of the Looch";
	private static final String creditsLabelText = "Brad Garton";
	private static final Font timesLargeBold = new Font("Times", Font.BOLD, 14);
	private static final Font timesMedium = new Font("Times", Font.PLAIN, 10);
	private static final Font helveticaSmall = new Font("Helvetica", Font.PLAIN, 8);

	private static final DroneNotes droneyThread = new DroneNotes();
	private static final SeqNotes seqThread = new SeqNotes();
	private static final WarbleNote warbleThread = new WarbleNote();
	private static final BurstNote burstThread = new BurstNote();
	private static final Speckle speckleThread = new Speckle(210, 350);

	private final Image loochIcon;
	private final Image background;

	public SwingLooch() {
		super(frameTitle);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		setSize(windowDimension);
		setResizable(false);

		loochIcon = new ImageIcon(iconPath).getImage();
		setIconImage(loochIcon);

		setLayout(null); // We're manually positioning everything!

		// Create background image
		background = new BufferedImage(210, 350, BufferedImage.TYPE_INT_RGB);

		// Add toggles
		ThreadToggleButton dronesToggle = new ThreadToggleButton(droneyThread, new Point(32, 30));
		ThreadToggleButton sequencesToggle = new ThreadToggleButton(seqThread, new Point(77, 30));
		ThreadToggleButton warblesToggle = new ThreadToggleButton(warbleThread, new Point(122, 30));
		ThreadToggleButton burstsToggle = new ThreadToggleButton(burstThread, new Point(167, 30));
		add(dronesToggle);
		add(sequencesToggle);
		add(warblesToggle);
		add(burstsToggle);

		// Add scrollbars
		ThreadScrollbar dronesScrollbar = new ThreadScrollbar(droneyThread, 0,
				new Color((float) 0.1, (float) 0.7, (float) 0.7), new Point(25, 45));
		ThreadScrollbar sequencesScrollbar = new ThreadScrollbar(seqThread, 79,
				new Color((float) 0.2, (float) 0.6, (float) 0.7), new Point(70, 45));
		ThreadScrollbar warblesScrollbar = new ThreadScrollbar(warbleThread, 86,
				new Color((float) 0.3, (float) 0.5, (float) 0.7), new Point(115, 45));
		ThreadScrollbar burstsScrollbar = new ThreadScrollbar(burstThread, 88,
				new Color((float) 0.4, (float) 0.4, (float) 0.7), new Point(160, 45));
		add(dronesScrollbar);
		add(sequencesScrollbar);
		add(warblesScrollbar);
		add(burstsScrollbar);

		// Add main button
		List<ThreadToggleButton> toggleButtons = new ArrayList<ThreadToggleButton>();
		toggleButtons.add(dronesToggle);
		toggleButtons.add(sequencesToggle);
		toggleButtons.add(warblesToggle);
		toggleButtons.add(burstsToggle);

		DroneButton droneButton = new DroneButton(toggleButtons);
		add(droneButton);

		RecordingButton recordingButton = new RecordingButton();
		add(recordingButton);
		addWindowListener(new LoochWindowListener(recordingButton));

		setVisible(true);
	}

	public void paint(Graphics graphics) {
		super.paint(graphics);

		// First-time setup for the background speckle effect.
		if (!speckleThread.isAlive()) {
			Graphics backgroundGraphics = background.getGraphics();
			backgroundGraphics.setColor(Color.white);
			backgroundGraphics.fillRect(0, 0, 210, 350);
			speckleThread.setGraphics(getGraphics(), backgroundGraphics);
			speckleThread.start();
		}

		graphics.drawImage(background, 0, 0, this);
		graphics.drawImage(loochIcon, 3, 327, this);

		// Draw all labels
		graphics.setColor(Color.black);

		graphics.setFont(timesMedium);
		graphics.drawString("drones", 19, 285);
		graphics.drawString("sequences", 56, 285);
		graphics.drawString("warbles", 109, 285);
		graphics.drawString("noises", 156, 285);

		graphics.setFont(timesLargeBold);
		graphics.drawString(controlLabelText, 28, 45);

		graphics.setFont(helveticaSmall);
		graphics.drawString(creditsLabelText, 155, 342);
	}

	public static void main(String[] args) {
		try {
			Synth.startEngine(0);
			new SwingLooch();
		} catch (SynthException e) {
			SynthAlert.showError(e);
		}
	}
}
