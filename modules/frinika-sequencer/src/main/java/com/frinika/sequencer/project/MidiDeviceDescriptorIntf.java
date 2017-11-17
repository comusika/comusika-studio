package com.frinika.sequencer.project;

import java.io.Serializable;
import javax.sound.midi.MidiDevice;
import javax.swing.Icon;

public interface MidiDeviceDescriptorIntf {

	public String getMidiDeviceName();

	/**
	 * Set the name of the midi device as registered in MidiSystem
	 *
	 * @param midiDeviceName
	 */
	public void setMidiDeviceName(String midiDeviceName);

	/**
	 * Get the name of the midi device as the user has set for it in the Frinika
	 * project
	 *
	 * @return
	 */
	public String getProjectName();

	/**
	 * Set the name of the midi device as the user wants to name it in the
	 * Frinika project
	 *
	 * @param projectName
	 */
	public void setProjectName(String projectName);

	public MidiDevice getMidiDevice();

	public Serializable getSerializableMidiDevice();

	public Icon getIcon();

	public Icon getLargeIcon();

    /**
     * @param midiDevice the midiDevice to set
     */
    public void setMidiDevice(MidiDevice midiDevice);
}
