#!/bin/bash
mkdir test
cp ../../*.java test
cp ../*.java test
#cp /*.java test
cd test
javac *.java 
java PokerPlayer Player Match
cd ..
rm test/*
rmdir test
