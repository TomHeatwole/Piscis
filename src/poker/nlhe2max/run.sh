#!/bin/bash
mkdir test
cp ../../* test
cp ../* test
cp * test
cd test
javac *.java 
java Driver $classes
cd ..
rm test/*
rmdir test
