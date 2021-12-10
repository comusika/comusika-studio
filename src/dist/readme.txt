Comusika Studio
===============

Comusika Studio is a free and open source multiplatform music workstation software written in Java.

This is experimental mashup application combining various Java projects in a bigger pack.

Features
--------

 * Sequencer
 * Piano roll
 * Amiga-style tracker
 * Notation
 * MIDI and audio recording
 * Soft synths
 * Mixer
 * Effects
 * Mastering
 * VST/VSTi support

License
-------

Remember that this is free software with absolutely NO WARRANTY and no responsibility  
for damaging your system or speakers or hearing or anything else.

Use at own risk and keep volume control down before launching or starting to  
play anything. Otherwise you may experience loud sound or noise that may  
damage your speakers or even your hearing.

Before proceeding you should read and accept the GNU General Public License which is  
in the license.txt file and you can also read more here:

GNU General Public License version 2.0 or later (GPLv2+) 

https://www.gnu.org/licenses/gpl-2.0.html

Installation requirements and run
---------------------------------

Make sure that you have Java 8 or greater installed (http://www.java.com)

If you've downloaded this as a zip file, remember to extract all the files.

From the extracted folder either launch comusika-studio.exe from your desktop  
(or comusika-studio.jar if supported), or enter a command prompt terminal and type:

java -jar comusika-studio.jar

If you want lowest latency possible you can try the following:

Windows users:  
java -DuseASIOAudioServer=true -jar comusika-studio.jar

Linux users:  
Start Jack before launching Comusika and setup audio to use it

Mac OSX (warning: Sometimes using this option causes a terrible noise,  
so turn the volume down before launching. If it happens, try restarting):
java -DuseOSXAudioServer=true -jar comusika-studio.jar
