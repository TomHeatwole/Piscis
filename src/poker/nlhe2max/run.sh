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
            sed 's/'"$name"'/Bot1/g' "$fullName" > Bot1.java
            runCommand+="java BotDriver 1 & "
        elif [ "$lang" == "python" ]
        then
            sed 's/'"$name"'/Bot1/g' "$fullName" > Bot1.py
            runCommand+="python BotDriver 1 & "
        fi
    else
        if [ "$lang" == "java" ]
        then
            sed 's/'"$name"'/Bot2/g' "$fullName" > Bot2.java
            runCommand+="java BotDriver 2 & "
        elif [ "$lang" == "python" ]
        then
            sed 's/'"$name"'/Bot2/g' "$fullName" > Bot2.py
            runCommand+="python BotDriver 2 & "
        fi
    fi
done < "$botNames"
javac *.java 
runCommand+="java Driver"
eval $runCommand
cd ..
rm -rf test
