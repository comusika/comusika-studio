package com.frinika.sequencer.gui.transport;

import static com.frinika.localization.CurrentLocale.getMessage;
import com.frinika.sequencer.FrinikaSequencer;
import com.frinika.sequencer.gui.RecordingDialog;
import com.frinika.sequencer.project.AbstractSequencerProjectContainer;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

public class RecordAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FrinikaSequencer sequencer;
	private AbstractSequencerProjectContainer project;
	
	public RecordAction(AbstractSequencerProjectContainer project) {
		super(getMessage("sequencer.project.record"));
		this.sequencer=project.getSequencer();	
		this.project=project;
	}
	
	
        @Override
    public void actionPerformed(ActionEvent e) {
        
        final JOptionPane recordingOptionPane = new JOptionPane(getMessage("sequencer.recording.takes"));
        
        //Frame to pop-up while recording to display each take
        final RecordingDialog recordingDialog = new RecordingDialog(recordingOptionPane,sequencer);

        recordingOptionPane.addPropertyChangeListener(new PropertyChangeListener() {

                @Override
            public void propertyChange(PropertyChangeEvent evt) {
                String prop = evt.getPropertyName();

                if (recordingDialog.isVisible() 
                 && (evt.getSource() == recordingOptionPane)
                 && (prop.equals(JOptionPane.VALUE_PROPERTY))) {                    
                    int[] deployableTakes = recordingDialog.getDeployableTakes();
                    if(deployableTakes.length>0)
                    {
                        project.getEditHistoryContainer().mark(getMessage("recording"));
                        sequencer.deployTake(deployableTakes);
                        project.getEditHistoryContainer().notifyEditHistoryListeners();
                    }
                    sequencer.stop();
                }
            }
        });
        
        
        sequencer.startRecording();
    }
	
}
