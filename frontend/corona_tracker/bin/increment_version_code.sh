#!/bin/sh

version=$(cat pubspec.yaml| grep "version:" | cut -d " " -f 2 | cut -d "+" -f 1)
currentBuildNumber=$(cat pubspec.yaml| grep "version:" | cut -d "+" -f 2)
newBuildNumber=$(expr $currentBuildNumber + 1)
awk '!/version:/' pubspec.yaml > temp && mv temp pubspec.yaml
echo "version: ${version}+${newBuildNumber}" >> pubspec.yaml
