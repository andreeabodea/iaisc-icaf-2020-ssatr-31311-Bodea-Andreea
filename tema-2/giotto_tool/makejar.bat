\cygwin\bin\find . -name "*.class" > classes.list
\cygwin\bin\find . -name "*.dat" >> classes.list
\cygwin\bin\find giotto/giottoc -name "*.txt" >> classes.list

jar cf gdk.jar @classes.list
