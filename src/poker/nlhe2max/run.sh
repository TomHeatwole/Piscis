#!/bin/bash
mkdir test
cp ../../* test
cp ../* test
cp * test
cd test
touch MatchToBot1.txt
touch MatchToBot2.txt
touch Bot1ToMatch.txt
touch Bot2ToMatch.txt
readBot1=true;
botNames="botNames.txt"
runCommand=""
while IFS= read -r name
do
    if [ "$readBot1" == true ]
    then
        sed 's/'"$name"'/Bot1/g' "$name".java > Bot1.java
        runCommand+="java BotDriver 1 & "
        readBot1=false;
    else
        sed 's/'"$name"'/Bot2/g' "$name".java > Bot2.java
        runCommand+="java BotDriver 2 & "
    fi
done < "$botNames"
javac *.java 
runCommand+="java Driver"
eval $runCommand
cd ..
rm -rf test
