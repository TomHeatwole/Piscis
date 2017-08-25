#!/bin/bash
mkdir test
cp ../../* test
cp ../* test
cp * test
cd test
javac BotDriver.java Bot1.java
javac BotDriver.java Bot2.java
compile=""
for java in *.java
do      
    if [ "$java" != "BotDriver" ] && [ "$java" != "Bot1" ] && [ "$java" != "Bot2" ]
    then
        compile="$compile $java"
    fi
done
javac $compile
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
touch Bot1ToMatch.txt
touch Bot2ToMatch.txt
touch MatchToBot1.txt
touch MatchToBot2.txt
#java Driver $classes & java BotDriver Bot1 & java BotDriver Bot2 & 
mv *.txt ../
cd ..
rm test/*
rmdir test
