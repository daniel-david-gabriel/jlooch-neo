package jlooch.swing;

import java.awt.Color;
import java.awt.Point;
import java.awt.Scrollbar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import jlooch.SoundThread;

public class ThreadScrollbar extends Scrollbar {
	private static final long serialVersionUID = 1L;

	public ThreadScrollbar(final SoundThread soundThread, final Integer initialProbability, final Color scrollColor, final Point location) {
		super(Scrollbar.VERTICAL, initialProbability, 10, 0, 110);
		addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
				Scrollbar scrollbar = (Scrollbar) adjustmentEvent.getAdjustable();
								
				if (!scrollbar.getValueIsAdjusting()) {
					Integer value = scrollbar.getValue();
					Double probability = (100-value)/100.0;

					if (soundThread.isRunning()) {
						soundThread.setProb(probability);
					}
				}
			}
		});
		setBackground(scrollColor);
		
		setSize(25,200);
		setLocation(location);
	}
}
