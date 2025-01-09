package GraphDataStructure;

public class Edge {
    public Node start, end;
    public int weight;

    public Edge(Node start, Node end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }
}