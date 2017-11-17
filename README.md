Frinika Studio - Java Digital Audio Workstation
===============================================

Frinika Studio is fork of frinika (http://frinika.com)Frinthesia is free game / learning tool for piano keyboards / MIDI instruments build.

Screenshot
----------

![Frinthesia Screenshot](images/screenshot.png?raw=true)

Homepage: https://github.com/hajdam/frinthesia (temporary)

Code is based on Frinika music workstation software: http://frinika.com

Development Plan
----------------

- Basic Steps:
  - Swing GUI application opening MIDI file
  - Rotate Frinika's Piano roll
  - MIDI instrument playing matching to MIDI file to compute score
  - Adding "wait to play" mode
  - Browsing MIDI files on local disc drives

- Advanced Steps:
  - Changing graphical appearance
  - MIDI keyboards setup
  - Mode for notes displaying
  ...

Structure
---------

As the project is currently in alpha stage, repository contains complete resources for distribution package with following folders:

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
