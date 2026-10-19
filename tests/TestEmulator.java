package emulator.tests;

import emulator.src.ShellEmulator;

public class TestEmulator {
    public static void main(String[] args) {
        testParseCommand();
        System.out.println("Stage 1 tests passed succesfully!");
    }

    private static void testParseCommand() {
        ShellEmulator emulator = new ShellEmulator();
        String[] tokens = emulator.parseCommand("ls -la /home");
        if (tokens.length != 3 || !"ls".equals(tokens[0])) {
            throw new RuntimeException("testParseComand failed");
        }
    }
}
