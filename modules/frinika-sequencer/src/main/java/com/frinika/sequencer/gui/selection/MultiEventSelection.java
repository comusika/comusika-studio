package com.frinika.sequencer.gui.selection;

import com.frinika.sequencer.model.MultiEvent;
import com.frinika.sequencer.model.Part;
import com.frinika.sequencer.project.AbstractSequencerProjectContainer;

public class MultiEventSelection extends SelectionContainer<MultiEvent>{

	
	
	public MultiEventSelection(AbstractSequencerProjectContainer project){
		super(project);
	}
	protected void setMetaFocus() {
		MultiEvent ev=(MultiEvent)focus;
		Part part=ev.getPart();
		project.getPartSelection().setFocus(part);		
	}


}
