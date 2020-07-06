Consonica Studio
================

Consonica Studio is a free and open source multiplatform music workstation software written in Java.

This is experimental mashup application combining various Java projects in a bigger pack.

Screenshot
----------

![Consonica Studio Screenshot](images/screenshot.png?raw=true)

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

GNU General Public License version 2.0 or later (GPLv2+)

See https://www.gnu.org/licenses/gpl-2.0.html or LICENSE.txt

Structure
---------

 * doc - Documentation + related presentations
 * gradle - Gradle wrapper
 * src - Sources related to building distribution packages
 * modules - Application parts and library modules
 * plugins - Optional plugins
 * tools - Distributable applictions or runnable subprojects and utilities
 * lib - External libraries
 * resources - Related resource files, like sample files, images, etc.

Compiling
---------

Java Development Kit (JDK) version 8 or later is required to build this project.

For project compiling Gradle 4.0 or later build system is used. You can either  
download and install gradle or use gradlew script which will download separate copy of gradle by itself.

Build system website: http://gradle.org  
Build command: gradle distZip
