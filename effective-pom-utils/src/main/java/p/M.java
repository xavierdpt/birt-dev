package p;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static p.NodesSupport.stream;

public class M {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(new File("/home/birt/xavierdpt/birt/effective-pom.xml"));
        System.out.println("Document could be parsed");
        Element rootNode = document.getDocumentElement();
        String tagName = rootNode.getTagName();
        System.out.println(tagName);
        Map<String, MavenPlugin> mavenPlugins = new HashMap<>();

        mavenPlugins = stream(rootNode.getChildNodes())
                .filter(node -> "project".equals(node.getNodeName()))
                .flatMap(node -> stream(node.getChildNodes()))
                .filter(node -> "build".equals(node.getNodeName()))
                .flatMap(node -> stream(node.getChildNodes()))
                .filter(node -> "plugins".equals(node.getNodeName()))
                .flatMap(node -> stream(node.getChildNodes()))
                .filter(node -> "plugin".equals(node.getNodeName()))
                .map(M::collectPlugin)
                .collect(Collectors.toMap(MavenPlugin::getKey, identity(), (x, y) -> Optional.ofNullable(x).orElse(y)));

        for (MavenPlugin mavenPlugin : mavenPlugins.values()) {
            System.out.println(mavenPlugin.getDescribeCommand());
        }

    }

    private static MavenPlugin collectPlugin(Node pluginsNode) {
        MavenPlugin mavenPlugin = new MavenPlugin();
        for (Node pluginNode : NodesSupport.iter(pluginsNode.getChildNodes())) {
            String pluginNodeName = pluginNode.getNodeName();
            if ("groupId".equals(pluginNodeName)) {
                String text = findText(pluginNode);
                mavenPlugin.setGroupId(text);
            }
            if ("artifactId".equals(pluginNodeName)) {
                String text = findText(pluginNode);
                mavenPlugin.setArtifactId(text);
            }
            if ("version".equals(pluginNodeName)) {
                String text = findText(pluginNode);
                mavenPlugin.setVersion(text);
            }

        }
        return mavenPlugin;
    }

    private static String findText(Node node) {
        for (Node childNode : NodesSupport.iter(node.getChildNodes())) {
            if ("#text".equals(childNode.getNodeName())) {
                return childNode.getNodeValue();
            }
        }
        return null;
    }
}
