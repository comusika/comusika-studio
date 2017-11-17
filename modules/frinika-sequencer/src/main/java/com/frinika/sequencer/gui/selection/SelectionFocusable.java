package com.frinika.sequencer.gui.selection;

import com.frinika.sequencer.model.Selectable;
import java.util.Collection;
/**
 * Generic interface for SelectionContainers for Cut/Copy/Paste operations.
 * @author Paul
 *
 */
public interface SelectionFocusable {
	public Collection<Selectable> getObjects();
	public void clearSelection();
}
