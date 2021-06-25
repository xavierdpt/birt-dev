package p;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Iterator;

public class NodesIterable implements Iterable<Node> {

    private NodeList nodes;

    public NodesIterable(NodeList nodes) {
        this.nodes = nodes;
    }

    @Override
    public Iterator<Node> iterator() {
        return new NodesIterator(nodes);
    }
}
