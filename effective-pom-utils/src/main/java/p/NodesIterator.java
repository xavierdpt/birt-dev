package p;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Iterator;

public class NodesIterator implements Iterator<Node> {

    private final NodeList nodes;
    private int index = 0;

    public NodesIterator(NodeList nodes) {
        this.nodes = nodes;
    }

    @Override
    public boolean hasNext() {
        return index < nodes.getLength();
    }

    @Override
    public Node next() {
        return nodes.item(index++);
    }
}
