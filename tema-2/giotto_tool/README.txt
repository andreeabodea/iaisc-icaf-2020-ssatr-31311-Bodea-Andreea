Giotto 1.0.1
$Id: README.txt,v 1.2 2004/10/01 02:43:14 cxh Exp $

The Giotto system is a programming methodology for embedded control
systems running on possibly distributed platforms. The Giotto system
consists of a time-triggered programming language, a compiler, and a
runtime system. Giotto aims at hard real-time applications with
periodic behavior.

For more information, see http://www-cad.eecs.berkeley.edu/~giotto
or email giotto@www-cad.eecs.berkeley.edu

Installation Instructions
-------------------------
gdk.jar contains the .class files, so you need not recompile.

If you would like to recompile anyway, follow the instructions below:
 * Cygwin or other Unix like platforms: 
    ./compile.sh

 * MSDOS:
    .\compile.bat

 * Eclipse: You may need to exclude the following files
    ./platform/emachine/javasim/GiottoClient.java
    ./platform/emachine/javasim/JSimEmulator.java
    ./platform/emachine/javasim/JSimScheduler.java

Startup
-------
The Giotto GUI is started by cd'ing to the top level giotto directory
and doing:
     java giotto.gdk.Start

You may need to set your classpath, or you can also use the jar file
     java -jar gdk.jar

Tutorial
--------
The Tutorial can be found at
  doc/GiottoTutorial.pdf
