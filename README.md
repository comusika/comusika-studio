Frinika Studio
==============

Frinika Studio is experimental fork of frinika (http://frinika.com).

Screenshot
----------

![Frinika Studio Screenshot](images/screenshot.png?raw=true)

Frinika is a free, complete music workstation software for Linux, Windows, Mac OSX and other operating systems running Java. 
It features sequencer, soft-synths, realtime effects and audio recording.

Some of the features:

<ul>
<li>Sequencer</li>
<li>Piano roll</li>
<li>Amiga-style tracker</li>
<li>Notation</li>
<li>Audio recording</li>
<li>Soft synths</li>
<li>Mixer</li>
<li>Effects</li>
<li>Mastering</li>
<li>VST/VSTi support</li>
</ul>

Code is based on Frinika music workstation software: http://frinika.com

Structure
---------

 * doc - Documentation + related presentations
 * gradle - Gradle wrapper
 * src - Sources related to building distribution packages
 * modules - Libraries and other
 * plugins - Catalog plugins
 * resources - Related resource files, like sample files, images, etc.
 * tools - Distributable subprojects encapsulating modules to runnable applications

Compiling
---------

Java Development Kit (JDK) version 7 or later is required to build this project.

For project compiling Gradle 2.0 build system is used. You can either download and install gradle and run "gradle distZip" command in project folder or gradlew or gradlew.bat scripts to download separate copy of gradle to perform the project build.

Build system website: http://gradle.org

Development
-----------

The Gradle build system provides support for various IDEs. See gradle website for more information.

 * Eclipse 3.7 or later

   Install Gradle integration plugin: http://marketplace.eclipse.org/content/gradle-integration-eclipse-0

 * NetBeans 8.0 or later

   Install Gradle support plugin: http://plugins.netbeans.org/plugin/44510/gradle-support

License
-------

GNU General Public License version 2.0 (GPLv2)
