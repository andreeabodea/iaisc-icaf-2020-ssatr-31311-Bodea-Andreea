#! /bin/sh
# Create gdk.jar from the .class and .dat files

classes=`find -name "*.class"`
dats=`find -name "*.dat"`
txts=`find giotto/giottoc -name "*.txt"`

# We use the manifest so that java -jar gdk.jar starts up the GUI
echo "Main-Class: giotto.gdk.Start" > gdk-jar.manifest

jar cmf gdk-jar.manifest gdk.jar copyright.txt README.txt $classes $dats $txts
