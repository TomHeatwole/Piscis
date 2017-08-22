#!/bin/bash
mkdir test
cp ../../*.java test
cp ../*.java test
cp *.java test
cd test
javac *.java 
strindex() {
    x="${1%%$2*}"
    [[ "$x" = "$1" ]] && echo -1 || echo "${#x}"
}
classes=""
for file in *.class
do      
    pos=$(strindex $file .class)
    className=$( echo $file | cut -c1-$pos )
    if [ "$className" != "Driver" ]
    then
        classes="$classes $className"
    fi
done
echo $classes 
java Driver $classes
cd ..
rm test/*
rmdir test
