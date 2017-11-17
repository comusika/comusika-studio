package com.frinika.sequencer.gui.transport;

import static com.frinika.localization.CurrentLocale.getMessage;
import com.frinika.sequencer.FrinikaSequencer;
import com.frinika.sequencer.project.AbstractSequencerProjectContainer;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

public class StopAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FrinikaSequencer sequencer;
	private AbstractSequencerProjectContainer project;
	
	public StopAction(AbstractSequencerProjectContainer project) {
		super(getMessage("sequencer.project.start_stop"));
		this.sequencer=project.getSequencer();
		this.project=project;
		
	//	putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke(
	//			KeyEvent.VK_SPACE,0));
	}
	
	public void actionPerformed(ActionEvent arg0) {
		
		
		if (sequencer.isRunning()) {
			  boolean recording = sequencer.isRecording();
		
			sequencer.stop();
		
			if(recording)
        
            if(sequencer.getNumberOfTakes() == 1)
            {
                project.getEditHistoryContainer().mark(getMessage("recording"));
                sequencer.deployTake(new int[] {sequencer.getNumberOfTakes()-1});
                project.getEditHistoryContainer().notifyEditHistoryListeners();
            }
        }
	}
	
}
