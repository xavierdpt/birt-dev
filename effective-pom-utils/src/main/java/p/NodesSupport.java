package p;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class NodesSupport {
    public static Stream<Node> stream(NodeList childNodes) {
        return StreamSupport.stream(new NodesIterable(childNodes).spliterator(), false);
    }

    public static NodesIterable iter(NodeList childNodes) {
        return new NodesIterable(childNodes);
    }
}
