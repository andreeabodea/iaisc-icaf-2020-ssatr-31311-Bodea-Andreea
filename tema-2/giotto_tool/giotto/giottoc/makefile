#  Copyright (c) 2002-2004 The Regents of the University of California.
#  All rights reserved.
#
#  Permission is hereby granted, without written agreement and without
#  license or royalty fees, to use, copy, modify, and distribute this
#  software and its documentation for any purpose, provided that the above
#  copyright notice and the following two paragraphs appear in all copies
#  of this software.
#
#  IN NO EVENT SHALL THE UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY
#  FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES
#  ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
#  THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
#  SUCH DAMAGE.
#
#  THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY WARRANTIES,
#  INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
#  MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
#  PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
#  CALIFORNIA HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES,
#  ENHANCEMENTS, OR MODIFICATIONS.
#
#  GIOTTO_COPYRIGHT_VERSION_2
#  COPYRIGHTENDKEY
##########################################################################
# Makefile for giottoc compiler and e machine target
#
# Author: Christoph Kirsch, cm@eecs.berkeley.edu
#
# Caution: Don't remove any tab characters.
# A tab character is used to distinguish dependencies from commands.
##########################################################################
# User parameters

# HOME :=
# CLASSPATH :=
# JAVABINPATH :=

##########################################################################
# SableCC path (parser generator)

SABLEPATH := $(HOME)/sablecc-2.16.2

##########################################################################
# giottoc paths

ANALYSISPATH := analysis
NODEPATH := node
LEXERPATH := lexer
PARSERPATH := parser

PARSER := $(PARSERPATH)/Parser.java

EXAMPLEPATH := examples

EXAMPLE := $(EXAMPLEPATH)/control.giotto

#GIOTTOCFLAGS := -annotated

##########################################################################
# e machine paths

EMACHINEPATH := emachine
UPLOADERPATH := uploader

ifdef WINDOWS
EMACHINE := ./e-machine.exe
UPLOADER := ./up-loader.exe
else
EMACHINE := ./e-machine
UPLOADER := ./up-loader
endif

FTABLEPATH := $(EXAMPLEPATH)/ftable
FCODEPATH := $(EXAMPLEPATH)/fcode
STABLEPATH := $(EXAMPLEPATH)/stable
SCODEPATH := $(EXAMPLEPATH)/scode
ECODEPATH := $(EXAMPLEPATH)/ecode
HTABLEPATH := $(EXAMPLEPATH)/htable
HCODEPATH := $(EXAMPLEPATH)/hcode
NTABLEPATH := $(EXAMPLEPATH)/ntable
NCODEPATH := $(EXAMPLEPATH)/ncode
OILPATH := $(EXAMPLEPATH)/oil

SIMULINKPATH := $(EXAMPLEPATH)/translated_reference_model_ert_rtw

##########################################################################
# Get all source files

EMACHINESOURCES := $(wildcard $(EMACHINEPATH)/*.c)
EMACHINEHEADERS := $(wildcard $(EMACHINEPATH)/*.h)
EMACHINEBACKUPS := $(wildcard $(EMACHINEPATH)/*~)
EMACHINEBINARIES := $(patsubst %.c,%.o,$(EMACHINESOURCES))

UPLOADERSOURCES := $(wildcard $(UPLOADERPATH)/*.c)
UPLOADERHEADERS := $(wildcard $(UPLOADERPATH)/*.h)
UPLOADERBACKUPS := $(wildcard $(UPLOADERPATH)/*~)
UPLOADERBINARIES := $(patsubst %.c,%.o,$(UPLOADERSOURCES))

FTABLESOURCES := $(wildcard $(FTABLEPATH)/*.c)
FTABLEHEADERS := $(wildcard $(FTABLEPATH)/*.h)
FTABLEBACKUPS := $(wildcard $(FTABLEPATH)/*~)
FTABLEBINARIES := $(patsubst %.c,%.o,$(FTABLESOURCES))

FCODESOURCES := $(wildcard $(FCODEPATH)/*.c)
FCODEHEADERS := $(wildcard $(FCODEPATH)/*.h)
FCODEBACKUPS := $(wildcard $(FCODEPATH)/*~)
FCODEBINARIES := $(patsubst %.c,%.o,$(FCODESOURCES))

STABLESOURCES := $(wildcard $(STABLEPATH)/*.c)
STABLEHEADERS := $(wildcard $(STABLEPATH)/*.h)
STABLEBACKUPS := $(wildcard $(STABLEPATH)/*~)
STABLEBINARIES := $(patsubst %.c,%.o,$(STABLESOURCES))

SCODESOURCES := $(wildcard $(SCODEPATH)/*.c)
SCODEHEADERS := $(wildcard $(SCODEPATH)/*.h)
SCODEBACKUPS := $(wildcard $(SCODEPATH)/*~)
SCODEBINARIES := $(patsubst %.c,%.o,$(SCODESOURCES))

ECODESOURCES := $(wildcard $(ECODEPATH)/*.c)
ECODEHEADERS := $(wildcard $(ECODEPATH)/*.h)
ECODEBACKUPS := $(wildcard $(ECODEPATH)/*~)
ECODEBINARIES := $(patsubst %.c,%.o,$(ECODESOURCES))

HTABLESOURCES := $(wildcard $(HTABLEPATH)/*.c)
HTABLEHEADERS := $(wildcard $(HTABLEPATH)/*.h)
HTABLEBACKUPS := $(wildcard $(HTABLEPATH)/*~)
HTABLEBINARIES := $(patsubst %.c,%.o,$(HTABLESOURCES))

HCODESOURCES := $(wildcard $(HCODEPATH)/*.c)
HCODEHEADERS := $(wildcard $(HCODEPATH)/*.h)
HCODEBACKUPS := $(wildcard $(HCODEPATH)/*~)
HCODEBINARIES := $(patsubst %.c,%.o,$(HCODESOURCES))

NTABLESOURCES := $(wildcard $(NTABLEPATH)/*.c)
NTABLEHEADERS := $(wildcard $(NTABLEPATH)/*.h)
NTABLEBACKUPS := $(wildcard $(NTABLEPATH)/*~)
NTABLEBINARIES := $(patsubst %.c,%.o,$(NTABLESOURCES))

NCODESOURCES := $(wildcard $(NCODEPATH)/*.c)
NCODEHEADERS := $(wildcard $(NCODEPATH)/*.h)
NCODEBACKUPS := $(wildcard $(NCODEPATH)/*~)
NCODEBINARIES := $(patsubst %.c,%.o,$(NCODESOURCES))

OILFILES := $(wildcard $(OILPATH)/*.oil)

SIMULINKSOURCES := $(wildcard $(SIMULINKPATH)/*.c)
SIMULINKHEADERS := $(wildcard $(SIMULINKPATH)/*.h)
SIMULINKBACKUPS := $(wildcard $(SIMULINKPATH)/*~)
SIMULINKBINARIES := $(patsubst %.c,%.o,$(SIMULINKSOURCES))

SOURCES := $(EMACHINESOURCES) \
           $(UPLOADERSOURCES) \
           $(FTABLESOURCES) $(FCODESOURCES) \
           $(STABLESOURCES) $(SCODESOURCES) \
           $(ECODESOURCES) \
           $(HTABLESOURCES) \
           $(NTABLESOURCES) \
           $(SIMULINKSOURCES)

HEADERS := $(EMACHINEHEADERS) \
           $(UPLOADERHEADERS) \
           $(FTABLEHEADERS) $(FCODEHEADERS) \
           $(STABLEHEADERS) $(SCODEHEADERS) \
           $(ECODEHEADERS) \
           $(HTABLEHEADERS) \
           $(NTABLEHEADERS) \
           $(SIMULINKHEADERS)

BACKUPS := $(EMACHINEBACKUPS) \
           $(UPLOADERBACKUPS) \
           $(FTABLEBACKUPS) $(FCODEBACKUPS) \
           $(STABLEBACKUPS) $(SCODEBACKUPS) \
           $(ECODEBACKUPS) \
           $(HTABLEBACKUPS) $(HCODEBACKUPS) \
           $(NTABLEBACKUPS) $(NCODEBACKUPS) \
           $(SIMULINKBACKUPS)

BINARIES := $(EMACHINEBINARIES) \
            $(FTABLEBINARIES) $(FCODEBINARIES) \
            $(STABLEBINARIES) $(SCODEBINARIES) \
            $(ECODEBINARIES) \
            $(HTABLEBINARIES) \
            $(NTABLEBINARIES) \
            $(SIMULINKBINARIES)

DISTBINARIES := $(HCODEBINARIES) $(NCODEBINARIES)
ALLBINARIES := $(BINARIES) $(DISTBINARIES) $(UPLOADERBINARIES)

ifdef DISTRIBUTED
HEADERS := $(HEADERS) $(HCODEHEADERS) $(NCODEHEADERS)
BINARIES := $(BINARIES) $(DISTBINARIES)
endif

##########################################################################
# POSIX THREADS LIBRARY PATHS

ifdef WINDOWS

# Windows
PTHREADSDIR := ../pthreads
PTHREADSLIBDIRECTIVE :=	-L$(PTHREADSDIR) -lpthreadGC -lwsock32

else

ifdef OSEK

# OSEK
PTHREADSDIR :=
PTHREADSLIBDIRECTIVE :=

else

# Linux
PTHREADSDIR :=
PTHREADSLIBDIRECTIVE :=	-lpthread

endif
endif

##########################################################################
# CLASSPATH

ifdef WINDOWS
CLASSPATHSEPARATOR := ;
else
ifdef OSEK
CLASSPATHSEPARATOR := ;
else
CLASSPATHSEPARATOR := :
endif
endif

#CLASSPATH := "$(CLASSPATH)$(CLASSPATHSEPARATOR).."

CLASSPATH := ../

##########################################################################
# JAVABINPATH

JAVACOMPILER := $(JAVABINPATH)javac
JVM := $(JAVABINPATH)java

##########################################################################
# Get all .java files in current directory and replace .java suffix by .class

JAVASOURCES := $(wildcard *.java)
JAVACLASSES := $(patsubst %.java,%.class,$(JAVASOURCES))

##########################################################################
# OSEK specific stuff for MPC 555 processor using DIAB compiler

ifdef OSEK

-include makefile-osek

else

##########################################################################
# C compiler flags

CC := gcc

CFLAGS := -I$(EMACHINEPATH) \
          -I$(UPLOADERPATH) \
          -I$(FTABLEPATH) -I$(FCODEPATH) \
          -I$(STABLEPATH) -I$(SCODEPATH) \
          -I$(ECODEPATH) \
          -I$(HTABLEPATH) \
          -I$(NTABLEPATH) \
          -I$(SIMULINKPATH) \
          -I$(PTHREADSDIR) \
          -DERT -DRT

endif # OSEK

ifdef DISTRIBUTED
CFLAGS := $(CFLAGS) -I$(HCODEPATH) -I$(NCODEPATH) -DDISTRIBUTED
endif

ifdef WINDOWS
CFLAGS := $(CFLAGS) -DWINDOWS
else
ifdef DYNAMIC
CFLAGS := $(CFLAGS) -DDYNAMIC
endif
endif

LDFLAGS := $(PTHREADSLIBDIRECTIVE) -lm

##########################################################################
# Define the rules
#
# Example:
# The first rule is the default rule and will be invoked by
# either 'gmake' or 'gmake giottoc'.

.PHONY : giottoc parser compile emachine uploader all run clean \
giottoc-clean parser-clean binary-clean backup-clean realclean

giottoc: parser $(JAVACLASSES)

parser: $(PARSER)

compile: $(ECODESOURCES)

ifdef OSEK

-include makefile-osek-rules

emachine: compile BUILD_ALL

else

emachine: compile $(EMACHINE)

endif # OSEK

uploader: $(UPLOADER)

all: parser giottoc compile emachine uploader

run: all
	$(EMACHINE)

clean: giottoc-clean binary-clean backup-clean

giottoc-clean:
	rm -f *~ *.class

parser-clean:
	rm -f $(ANALYSISPATH)/*.class $(NODEPATH)/*.class $(LEXERPATH)/*.class $(PARSERPATH)/*.class

binary-clean:
	rm -f $(ALLBINARIES) $(EMACHINE) $(UPLOADER)

backup-clean:
	rm -f $(BACKUPS)

realclean: clean parser-clean
	rm -rf $(ANALYSISPATH) $(NODEPATH) $(LEXERPATH) $(PARSERPATH)

##########################################################################
# Define the pattern rules
#
# Example:
# The first rule tells 'make' how to generate .class files from .java files.
# % matches any non-empty substring, e.g., 'giottoc'.
# Then $< is 'giottoc.java', $@ is 'giottoc.class', and $* is 'giottoc'.

%.class : %.java
	$(JAVACOMPILER) -classpath $(CLASSPATH) $<

$(PARSER): giottoc.grammar
	$(JVM) -jar $(SABLEPATH)/lib/sablecc.jar -d ../ $<

$(ECODESOURCES): $(EXAMPLE) $(JAVACLASSES)
	$(JVM) -classpath $(CLASSPATH) giottoc.GiottoC $(EXAMPLE) $(GIOTTOCFLAGS)

%.o : %.c $(HEADERS)
	$(CC) -g -c $(CFLAGS) $< -o $@

$(EMACHINE): $(BINARIES)
	$(CC) $^ -o $@ $(LDFLAGS)

$(UPLOADER): $(UPLOADERBINARIES)
	$(CC) $^ -o $@ $(LDFLAGS)
