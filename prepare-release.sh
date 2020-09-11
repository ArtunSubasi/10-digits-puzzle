#!/bin/bash

# This script is meant to be run by the Semantic Release plugin
# in the prepare phase. We assume that all tests are started before
# Semantic Release and they all passed.
if [ -z "$1" ] ; then
  echo "No version number supplied"
  exit 1
fi

# Set the release version
printf $1 > 'src/commonMain/resources/version.txt'

# Build the distribution packages
./gradlew jsWebMinWebpack packageJvmFatJarProguard

# Copy the JS files to the release directory and zip
cp -R build/web-min-webpack/ 10-digits-puzzle-js
zip 10-digits-puzzle-js.zip 10-digits-puzzle-js/*

# Copy jar to the release directory and rename
cp build/libs/*all-proguard.jar 10-digits-puzzle-jvm.jar