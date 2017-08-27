#!/bin/bash
mkdir test
cp ../../* test
cp ../* test
cp * test
cd test
readBot1=true;
botNames="botNames.txt"
while IFS= read -r name
do
    if [ "$readBot1" == true ]
    then
        sed 's/'"$name"'/Bot1/g' "$name".java > Bot1.java
        readBot1=false;
    else
        sed 's/'"$name"'/Bot2/g' "$name".java > Bot2.java
    fi
done < "$botNames"
javac *.java 
java Driver $classes
cd ..
rm test/*
rmdir test
