#!/usr/bin/env bash
set -e
cd "$(dirname "$0")"
mkdir -p build
javac -encoding UTF-8 -d build src/main/java/com/barber/*.java
cp -R src/main/resources/. build/
java -cp build com.barber.BarberApp
