#!/bin/bash
echo "=== Compiling Project ==="
mkdir -p out
javac -d out src/*.java tests/*.java

echo ""
echo "=== Running Java Unit Tests ==="
java -cp out emulator.tests.TestEmulator
