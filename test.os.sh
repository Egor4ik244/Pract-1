#!/bin/bash
echo "=== Compiling Project ==="
mkdir -p out
javac -d out src/*.java tests/*.java

echo ""
echo "=== Running Java Unit Tests ==="
java -cp out emulator.tests.TestEmulator

echo ""
echo "=== Test 1: Minimal VFS ==="
java -cp out emulator.src.Main --vfs vfs_minimal.xml --script startup_script.txt

echo ""
echo "=== Test 2: Multi-file VFS ==="
java -cp out emulator.src.Main --vfs vfs_files.xml --script startup_script.txt

echo ""
echo "=== Test 3: Deep VFS ==="
java -cp out emulator.src.Main --vfs vfs_deep.xml --script startup_script.txt
