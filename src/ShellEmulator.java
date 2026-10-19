package emulator.src;

import java.util.Scanner;

public class ShellEmulator {
    private final String vfsName;
    private boolean running;

    public ShellEmulator() {
        this.vfsName = "default_vfs";
        this.running = true;
    }

    public String[] parseCommand(String input) {
        if (input == null) {
            return new String[]{""};
        }
        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            return new String[]{""};
        }
        return trimmed.split("\\s+");
    }

    public void executeCommand(String[] tokens) {
        if (tokens == null || tokens.length == 0 || tokens[0].isEmpty()) {
            return;
        }
        String cmd = tokens[0];
        String[] args = new String[tokens.length - 1];
        for (int i = 1; i < tokens.length; i++) {
            args[i - 1] = tokens[i];
        }

        if ("exit".equals(cmd)) {
            this.running = false;
        } else if ("cd".equals(cmd) || "ls".equals(cmd)) {
            System.out.println("Stub " + cmd + ": arguments = [" + String.join(", ", args) + "]");
        } else {
            System.out.println("Error: unknoun command '" + cmd + "'");
        }
    }

    public void repl() {
        Scanner scanner = new Scanner(System.in);
        while (running) {
            System.out.print(vfsName + "> ");
            if (!scanner.hasNextLine()) {
                break;
            }
            String input = scanner.nextLine();
            String[] tokens = parseCommand(input);
            executeCommand(tokens);
        }
    }
}
