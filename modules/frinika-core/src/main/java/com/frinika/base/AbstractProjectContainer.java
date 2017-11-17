/*
 * Created on Mar 6, 2006
 *
 * Copyright (c) 2005 Peter Johan Salomonsen (http://www.petersalomonsen.com)
 * 
 * http://www.frinika.com
 * 
 * This file is part of Frinika.
 * 
 * Frinika is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.

 * Frinika is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with Frinika; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package com.frinika.base;

import com.frinika.model.EditHistoryContainer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import uk.org.toot.audio.mixer.MixerControls;

/**
 * Use to load Frinika projects.
 *
 * This class links together all components of a Frinika project, and provides
 * all operations and features - including a Frinika sequencer instance.
 *
 * Information about Midi Devices - naming and how to reopen them is contained
 * using the MidiDeviceDescriptors.
 *
 * Audio files are stored in a folder named audio which is created in the same
 * folder as where the project is. Thus a good convention is to have one folder
 * per project.
 *
 * @author Peter Johan Salomonsen
 */
public abstract class AbstractProjectContainer {

    // Modified listener for new/load/save events
    private static List<FrinikaProjectChangeListener> changeListeners = new ArrayList<>();
    private static AbstractProjectContainer mainProject;

    /**
     * @return the mainProject
     */
    public static AbstractProjectContainer getMainProject() {
        return mainProject;
    }

    public abstract MixerControls getMixerControls();

    public abstract void close();

    public static boolean removeChangeListener(FrinikaProjectChangeListener o) {
        return changeListeners.remove(o);
    }

    public static boolean addChangeListener(FrinikaProjectChangeListener e) {
        return changeListeners.add(e);
    }

    public static boolean addAndRunChangeListener(FrinikaProjectChangeListener e) {
        if (changeListeners.add(e)) {
            if (mainProject != null) {
                mainProject.close();
                e.newProject(mainProject);
            }
            return true;
        }
        return false;
    }

    /**
     * Listener for project changes.
     */
    public interface FrinikaProjectChangeListener {

        public void newProject(AbstractProjectContainer project);
    }

    public static void setMainProject(AbstractProjectContainer project) {
        AbstractProjectContainer.mainProject = project;
        for (Iterator<FrinikaProjectChangeListener> it = changeListeners.iterator(); it.hasNext();) {
            ((AbstractProjectContainer.FrinikaProjectChangeListener) it.next()).newProject(project);
        }
    }

    public abstract EditHistoryContainer getEditHistoryContainer();
}
