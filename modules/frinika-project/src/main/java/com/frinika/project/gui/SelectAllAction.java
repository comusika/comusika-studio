package com.frinika.project.gui;

import com.frinika.sequencer.gui.ProjectFrame;
import java.awt.event.KeyEvent;


public class SelectAllAction  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProjectFrame project;
	
	public SelectAllAction(ProjectFrame project) {
		this.project=project;		
	}
	
	public boolean selectAll(KeyEvent e) {
		if (project.getTrackerPanel().getTable().hasFocus())
		{
			project.getTrackerPanel().getTable().selectAll();
		}
		else
		if (project.getPartViewEditor().getPartview().getMousePosition() != null) {
			project.getPartViewEditor().getPartview().selectAll();
			
		} else if (project.getPianoControllerPane().getPianoRoll().getMousePosition() != null) { 
			
			project.getPianoControllerPane().getPianoRoll().selectAll();
		} else if (project.getPianoControllerPane().getControllerView().getMousePosition() != null) { 
			project.getPianoControllerPane().getControllerView().selectAll();
		} else {
			return true;
		}
		return false;
	}	
}
