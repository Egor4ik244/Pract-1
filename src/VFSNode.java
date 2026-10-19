package emulator.src;

import java.util.HashMap;
import java.util.Map;

public class VFSNode {
    private final String name;
    private final boolean isDirectory;
    private final byte[] content;
    private final Map<String, VFSNode> children;

    public VFSNode(String name, boolean isDirectory, byte[] content) {
        this.name = name;
        this.isDirectory = isDirectory;
        this.content = content;
        this.children = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public byte[] getContent() {
        return content;
    }

    public Map<String, VFSNode> getChildren() {
        return children;
    }
}
