package emulator.src;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class VFS {
    private String vfsName;
    private VFSNode root;

    public VFS() {
        this.vfsName = "default_vfs";
        this.root = new VFSNode("/", true, new byte[0]);
    }

    public VFSNode getRoot() {
        return root;
    }

    public String getVFSname() {
        return vfsName;
    }

    public void loadFromXml(String xmlPath) {
        try {
            File file = new File(xmlPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            Element rootElem = doc.getDocumentElement();
            this.vfsName = rootElem.getAttribute("name");
            if (this.vfsName == null || this.vfsName.isEmpty()) {
                this.vfsName = "vfs";
            }
            this.root = new VFSNode("/", true, new byte[0]);
            parseElement(rootElem, this.root);
        } catch (Exception e) {
            System.out.println("Error loading XML VFS: " + e.getMessage());
        }
    }

    private void parseElement(Element xmlElem, VFSNode currentNode) {
        NodeList nodelist = xmlElem.getChildNodes();
        for (int i = 0; i < nodelist.getLength(); i++) {
            Node node = nodelist.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;
                processXmlElement(elem, currentNode);
            }
        }
    }

    private void processXmlElement(Element elem, VFSNode currentNode) {
        String name = elem.getAttribute("name");
        String tag = elem.getTagName();

        if ("dir".equals(tag)) {
            VFSNode dirNode = new VFSNode(name, true, new byte[0]);
            currentNode.getChildren().put(name, dirNode);
            parseElement(elem, dirNode);
        } else if ("file".equals(tag)) {
            String encoding = elem.getAttribute("encoding");
            String textContent = elem.getTextContent().trim();
            byte[] content;

            if ("base64".equalsIgnoreCase(encoding)) {
                content = Base64.getDecoder().decode(textContent);
            } else {
                content = textContent.getBytes(StandardCharsets.UTF_8);
            }
            VFSNode fileNode = new VFSNode(name, false, content);
            currentNode.getChildren().put(name, fileNode);
        }
    }
}
