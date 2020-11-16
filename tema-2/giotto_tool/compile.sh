#!/bin/sh
# $Id: compile.sh,v 1.5 2004/09/28 23:04:11 cxh Exp $
# Shell Script to build all the java files

# We remove a few files that do not compile
find -name "*.java" | \
    grep -v ./platform/emachine/javasim/GiottoClient.java | \
    grep -v ./platform/emachine/javasim/JSimEmulator.java | \
    grep -v ./platform/emachine/javasim/JSimScheduler.java  \
        > file.list

javac -classpath . @file.list
