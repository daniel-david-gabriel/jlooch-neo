package jlooch;

public interface SoundThread {
	public void start();
	public void stopSound();
	public void setProb(double p);
	public Boolean isRunning();
}
