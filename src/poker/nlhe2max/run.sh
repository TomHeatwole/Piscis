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
touch Bot1.java
echo "public class Bot1{public Bot1(){} public String processMatchInfo(String s){return s;}}" > Bot1.java
touch Bot2.java
echo "public class Bot2{public Bot2(){} public String processMatchInfo(String s){return s;}}" > Bot2.java
readBot1=true;
botNames="botNames.txt"
runCommand=""
while IFS="\n" read -r fullName
do
    nameParts=(${fullName//./ }) 
    name=${nameParts[0]}
    lang=${nameParts[1]}
    if [ "$readBot1" == true ]
    then
        readBot1=false;
        if [ "$lang" == "java" ] 
        then
            rm Bot1.java
            sed 's/'"$name"'/Bot1/g' "$fullName" > Bot1.java
            runCommand+="java BotDriver 1 & "
        elif [ "$lang" == "py" ]
        then
            sed 's/'"$name"'/Bot1/g' "$fullName" > Bot1.py
            runCommand+="python BotDriver.py 1 & "
        fi
    else
        if [ "$lang" == "java" ]
        then
            rm Bot2.java
            sed 's/'"$name"'/Bot2/g' "$fullName" > Bot2.java
            runCommand+="java BotDriver 2 & "
        elif [ "$lang" == "py" ]
        then
            sed 's/'"$name"'/Bot2/g' "$fullName" > Bot2.py
            runCommand+="python BotDriver.py 2 & "
        fi
    fi
done < "$botNames"
javac *.java 
runCommand+="java Driver"
eval $runCommand
cd ..
rm -rf test
