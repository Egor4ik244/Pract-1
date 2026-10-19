package emulator.src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class ShellEmulator {
    private final VFS vfs;
    private final String vfsPath;
    private final String scriptPath;
    private boolean running;

    public ShellEmulator(String vfsPath, String scriptPath) {
        this.vfs = new VFS();
        this.vfsPath = vfsPath;
        this.scriptPath = scriptPath;
        this.running = true;

        if (vfsPath != null && !vfsPath.isEmpty()) {
            this.vfs.loadFromXml(vfsPath);
        }
    }

    public void printDebugInfo() {
        System.out.println("=== Debug Configuration ===");
        System.out.println("VFS Path: " + vfsPath);
        System.out.println("Script Path: " + scriptPath);
        System.out.println("VFS Name: " + vfs.getVFSname());
        System.out.println("===========================");
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

    public void runScript(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null && running) {
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("//") || trimmed.startsWith("#")) {
                    continue;
                }
                System.out.println(vfs.getVFSname() + "> " + trimmed);
                String[] tokens = parseCommand(trimmed);
                executeCommand(tokens);
            }
        } catch (Exception e) {
            System.out.println("Error executing script: " + e.getMessage());
        }
    }

    public void repl() {
        Scanner scanner = new Scanner(System.in);
        while (running) {
            System.out.print(vfs.getVFSname() + "> ");
            if (!scanner.hasNextLine()) {
                break;
            }
            String input = scanner.nextLine();
            String[] tokens = parseCommand(input);
            executeCommand(tokens);
        }
    }
}

