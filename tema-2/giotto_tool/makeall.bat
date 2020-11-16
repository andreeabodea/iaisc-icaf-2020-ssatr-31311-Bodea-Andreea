\cygwin\bin\find . -not -path  "*javasim*" -a -name "*.java" -print > file.list
javac @file.list
