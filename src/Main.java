package emulator.src;

public class Main {
    public static void main(String[] args) {
        String vfsPath = null;
        String scriptPath = null;

        for (int i = 0; i < args.length; i++) {
            if ("--vfs".equals(args[i]) && i + 1 < args.length) {
                vfsPath = args[++i];
            } else if ("--script".equals(args[i]) && i + 1 < args.length) {
                scriptPath = args[++i];
            }
        }

        ShellEmulator emulator = new ShellEmulator(vfsPath, scriptPath);
        emulator.printDebugInfo();

        if (scriptPath != null && !scriptPath.isEmpty()) {
            emulator.runScript(scriptPath);
        } else {
            emulator.repl();
        }
    }
}
