package com.frinika.sequencer;

import com.frinika.project.ProjectContainer;
import com.frinika.sequencer.model.MidiPart;
import com.frinika.sequencer.model.NoteEvent;
import com.frinika.sequencer.project.AbstractSequencerProjectContainer;
import junit.framework.TestCase;

/**
 * Unit test to test EditHistoryContainer
 * @author Peter Johan Salomonsen
 */
public class EditHistoryContainerTest extends TestCase {

    MidiPart part;
    AbstractSequencerProjectContainer proj;
    
    protected void setUp() throws Exception {
        super.setUp();
        
//      Create the audio context
        //new AudioContext();
        // Create the project container
        
        proj = new ProjectContainer();
        
        // Create a lane
        com.frinika.sequencer.model.MidiLane lane = proj.createMidiLane();

        // Create a MidiPart
        part = new MidiPart(lane);
    }

    public void testMultiEvents()
    {    
        // Add note
        
        NoteEvent noteEvent = new NoteEvent(part, 0,60, 100, 0, 128);
        proj.getEditHistoryContainer().mark("add event");
        part.add(noteEvent);
        proj.getEditHistoryContainer().mark("change event");
        part.remove(noteEvent);
        noteEvent.setVelocity(10);
        part.add(noteEvent);
        proj.getEditHistoryContainer().mark("remove event");
        part.remove(noteEvent);
        
        // Undo everything
        
        proj.getEditHistoryContainer().undo();
        assertEquals(noteEvent,part.getMultiEvents().first());
        assertEquals(10,noteEvent.getVelocity());
        proj.getEditHistoryContainer().undo();
        assertEquals(100,noteEvent.getVelocity());
        proj.getEditHistoryContainer().undo();
        assertEquals(0,part.getMultiEvents().size());

        // Redo everything
        
        proj.getEditHistoryContainer().redo();
        assertEquals(100,noteEvent.getVelocity());
        assertEquals(noteEvent,part.getMultiEvents().first());
        proj.getEditHistoryContainer().redo();
        assertEquals(10,noteEvent.getVelocity());
        proj.getEditHistoryContainer().redo();
        assertEquals(0,part.getMultiEvents().size());

        // Undo everything again
        
        proj.getEditHistoryContainer().undo();
        assertEquals(noteEvent,part.getMultiEvents().first());
        assertEquals(10,noteEvent.getVelocity());
        proj.getEditHistoryContainer().undo();
        assertEquals(100,noteEvent.getVelocity());
        proj.getEditHistoryContainer().undo();
        assertEquals(0,part.getMultiEvents().size());

    }
}
