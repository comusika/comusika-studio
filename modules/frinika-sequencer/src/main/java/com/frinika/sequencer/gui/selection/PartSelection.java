package com.frinika.sequencer.gui.selection;

import com.frinika.sequencer.model.Lane;
import com.frinika.sequencer.model.Part;
import com.frinika.sequencer.project.AbstractSequencerProjectContainer;

/**
 * 
 * @author Paul
 *
 */
public class PartSelection extends SelectionContainer<Part >{
	
	public PartSelection(AbstractSequencerProjectContainer project){
		super(project);
		
	}
	
	protected void setMetaFocus() {
		Part part=(Part)focus;
		Lane lane=part.getLane();
		project.getLaneSelection().setFocus(lane);		
	}
	
}
