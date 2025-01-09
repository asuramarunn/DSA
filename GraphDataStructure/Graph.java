package GraphDataStructure;
import java.util.List;
import java.util.ArrayList;

public class Graph {
    private List<Node> nodes;
    private List<Edge> edges;

    public Graph() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void addEdge(Node start, Node end, int weight) {
        edges.add(new Edge(start, end, weight));
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.start.equals(node)) {
                neighbors.add(edge.end);
            }
        }
        return neighbors;
    }

    // New method to get adjacency list
    public List<Edge> getAdjacencyList(Node node) {
        List<Edge> adjacencyList = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.start.equals(node)) {
                adjacencyList.add(edge);
            }
        }
        return adjacencyList;
    }

    public void removeNode(Node node) {
        nodes.remove(node);
        edges.removeIf(edge -> edge.start.equals(node) || edge.end.equals(node));
    }

    public void clear() {
        nodes.clear();
        edges.clear();
    }
}
