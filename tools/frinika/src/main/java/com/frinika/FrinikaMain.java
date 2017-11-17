package com.frinika;

import static com.frinika.base.FrinikaAudioSystem.getAudioServer;
import static com.frinika.base.FrinikaAudioSystem.installClient;
import static com.frinika.frame.FrinikaFrame.addProjectFocusListener;
import com.frinika.frame.WelcomeDialog;
import com.frinika.frame.action.CreateProjectAction;
import com.frinika.frame.action.OpenProjectAction;
import static com.frinika.frame.action.OpenProjectAction.setSelectedFile;
import static com.frinika.global.FrinikaConfig.SETUP_DONE;
import static com.frinika.global.FrinikaConfig.lastProjectFile;
import static com.frinika.global.Toolbox.extractFromJar;
import static com.frinika.localization.CurrentLocale.getMessage;
import com.frinika.project.ProjectContainer;
import static com.frinika.project.dialog.SplashDialog.closeSplash;
import static com.frinika.project.dialog.SplashDialog.showSplash;
import com.frinika.project.gui.ProjectFocusListener;
import static com.frinika.settings.SetupDialog.showSettingsModal;
import static com.frinika.tootX.midi.MidiInDeviceManager.close;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import static java.io.File.pathSeparator;
import java.io.IOException;
import static java.lang.Runtime.getRuntime;
import static java.lang.System.err;
import static java.lang.System.exit;
import static java.lang.System.getProperty;
import static java.lang.System.out;
import static java.lang.System.setProperty;
import static javax.swing.UIManager.setLookAndFeel;
import javax.swing.UnsupportedLookAndFeelException;

/*
 * Created on Mar 6, 2006
 *
 * Copyright (c) 2004-2006 Peter Johan Salomonsen (http://www.petersalomonsen.com)
 * 
 * http://www.frinika.com
 * 
 * This file is part of Frinika.
 * 
 * Frinika is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Frinika is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Frinika; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

/**
 * The main entry class for Frinika
 * 
 * @author Peter Johan Salomonsen
 */
public class FrinikaMain {

	static FrinikaExitHandler exitHook = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		prepareRunningFromSingleJar();
		
		configureUI();

		try {
			int n = 1;
	
			Object[] options = { getMessage("welcome.new_project"),
					getMessage("welcome.open_existing"),
					getMessage("welcome.settings"), getMessage("welcome.quit") };

			//String setup = FrinikaConfig.getProperty("multiplexed_audio");
			
			WelcomeDialog welcome= new WelcomeDialog(options);

			//if (setup == null) {
			if ( !SETUP_DONE ) {
			//	welcome = new WelcomeDialog(options);
				welcome.setModal(false);
				welcome.setVisible(true);
				showSettingsModal();
				welcome.setVisible(false);
			} 
			
			
			welcome.setModal(true);

			welcome.addButtonActionListener(2, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					showSettingsModal();
				}
			});

			welcome.setVisible(true);

		
			n = welcome.getSelectedOption();

			switch (n) {
			case -1:
				exit(0);
				break;
			case 0:
				// new ProjectFrame(new ProjectContainer());
				showSplash();
				new CreateProjectAction().actionPerformed(null);
				break;
			case 1:
				showSplash();
				String lastFile = lastProjectFile();
				if (lastFile != null)
					setSelectedFile(new File(lastFile));
				new OpenProjectAction().actionPerformed(null);
				break;
			case 3:
				exit(0);
				break;

			default:
				assert (false);
			}

		} catch (Exception e) {
			e.printStackTrace();
			exit(-1); // new ProjectFrame(new ProjectContainer());
		}

		exitHook = new FrinikaExitHandler();
		getRuntime().addShutdownHook(exitHook);

		addProjectFocusListener(new ProjectFocusListener() {

			public void projectFocusNotify(ProjectContainer project) {
				installClient(project.getAudioClient());
			}

		});

		closeSplash();

		getAudioServer().start();
	}

	public static void configureUI() {

		String lcOSName = getProperty("os.name").toLowerCase();

		try {
			setLookAndFeel(new PlasticXPLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

	}

	static class FrinikaExitHandler extends Thread {
		public void run() {
			close();
			close();
//			SwingUtilities.invokeLater(new Runnable() {
//				public void run() {
//					System.out.println(" Closing ALL midi devices ");
//					ProjectContainer.closeAllMidiOutDevices();
//				}
//			});
		}
	}

	/**
	 * Detect whether running from a single .jar-file (e.g. via "java -jar frinika.jar").
	 * In this case, copy native binary libraries to a file-system accessible location
	 * where the JVM can load them from.
	 * (There is a comparable mechanism already implemented in 
	 * com.frinika.priority.Priority, but this here works for all native libraries,
	 * esp. libjjack.so.)
	 * (Jens)
	 */
	public static void prepareRunningFromSingleJar() {
		String classpath = getProperty("java.class.path");
		if ( classpath.indexOf(pathSeparator) == -1 ) { // no pathSeparator: single entry classpath
			if (classpath.endsWith(".jar")) {
				File file = new File(classpath);
				if (file.exists() && file.isFile()) { // yes, running from 1 jar
					String osarch = getProperty("os.arch");
					String osname = getProperty("os.name");
					String libPrefix = "lib/" + osarch + "/" + osname + "/";
					String tmp = getProperty("java.io.tmpdir");
					File tmpdir = new File(tmp);
					try {
						out.println("extracting files from "+libPrefix+" to "+tmpdir.getAbsolutePath()+":");
						extractFromJar(file, libPrefix, tmpdir);
						setProperty("java.library.path", tmp);
					} catch (IOException ioe) {
						err.println("Native library extraction failed. Problems may occur.");
						ioe.printStackTrace();
					}
				}
			}
		}
	}
}
