import java.util.*;

import GraphDataStructure.*;

public class ShortestPathAlgorithms {

    // Dijkstra's Algorithm
    public static Map<Node, Integer> dijkstra(Graph graph, Node start) {
        Map<Node, Integer> distances = new HashMap<>();
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>(Comparator.comparingInt(nd -> nd.distance));

        // Initialize distances
        for (Node node : graph.getNodes()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        pq.add(new NodeDistance(start, 0));

        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll();

            for (Edge edge : graph.getEdges()) {
                if (edge.start.equals(current.node)) {
                    int newDist = distances.get(current.node) + edge.weight;
                    if (newDist < distances.get(edge.end)) {
                        distances.put(edge.end, newDist);
                        pq.add(new NodeDistance(edge.end, newDist));
                    }
                }
            }
        }

        return distances;
    }

    // A* Algorithm with Euclidean Distance heuristic
    public static Map<Node, Node> aStar(Graph graph, Node start, Node goal, Heuristic heuristic) {
        Map<Node, Integer> gScores = new HashMap<>();
        Map<Node, Integer> fScores = new HashMap<>();
        Map<Node, Node> cameFrom = new HashMap<>();
        PriorityQueue<NodeDistance> openSet = new PriorityQueue<>(
                Comparator.comparingInt(nd -> fScores.getOrDefault(nd.node, Integer.MAX_VALUE)));

        gScores.put(start, 0);
        fScores.put(start, heuristic.estimate(start, goal));
        openSet.add(new NodeDistance(start, fScores.get(start)));

        System.out.println("Initial fScore for " + start.name + ": " + fScores.get(start));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll().node;

            System.out.println("Processing node: " + current.name);
            System.out.println("fScore: " + fScores.getOrDefault(current, Integer.MAX_VALUE));
            System.out.println("gScore: " + gScores.getOrDefault(current, Integer.MAX_VALUE));

            if (current.equals(goal)) {
                System.out.println("Goal node reached!");
                System.out.println("cameFrom map: " + cameFrom);
                
                return cameFrom;
            }

            for (Edge edge : graph.getEdges()) {
                if (edge.start.equals(current)) {
                    int tentativeGScore = gScores.getOrDefault(current, Integer.MAX_VALUE) + edge.weight;

                    if (tentativeGScore < gScores.getOrDefault(edge.end, Integer.MAX_VALUE)) {
                        cameFrom.put(edge.end, current); // Update cameFrom
                        gScores.put(edge.end, tentativeGScore);
                        fScores.put(edge.end, tentativeGScore + heuristic.estimate(edge.end, goal));

                        System.out.println("Updating node: " + edge.end.name);
                        System.out.println("New gScore: " + tentativeGScore);
                        System.out.println("New fScore: " + fScores.get(edge.end));

                        openSet.add(new NodeDistance(edge.end, fScores.get(edge.end)));
                    }
                }
            }
        }

        System.out.println("No path found.");
        return null; // Path not found
    }

    // Greedy Best-First Search with Euclidean Distance heuristic
    public static Map<Node, Node> greedyBFS(Graph graph, Node start, Node goal, Heuristic heuristic) {
        Map<Node, Node> cameFrom = new HashMap<>();
        Set<Node> visited = new HashSet<>();
        PriorityQueue<NodeDistance> openSet = new PriorityQueue<>(Comparator.comparingInt(nd -> nd.distance));

        openSet.add(new NodeDistance(start, heuristic.estimate(start, goal)));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll().node;

            if (current.equals(goal)) {
                return cameFrom;
            }

            visited.add(current);

            for (Edge edge : graph.getEdges()) {
                if (edge.start.equals(current) && !visited.contains(edge.end)) {
                    cameFrom.put(edge.end, current);
                    openSet.add(new NodeDistance(edge.end, heuristic.estimate(edge.end, goal)));
                }
            }
        }

        return null; // Path not found
    }

    // Utility Classes
    private static class NodeDistance {
        Node node;
        int distance;

        NodeDistance(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    // Interface for Heuristic function
    public interface Heuristic {
        int estimate(Node current, Node goal);
    }

    // Euclidean Distance heuristic
    public static class EuclideanHeuristic implements Heuristic {
        @Override
        public int estimate(Node current, Node goal) {
            // Euclidean Distance between two nodes
            return (int) Math.sqrt(Math.pow(current.x - goal.x, 2) + Math.pow(current.y - goal.y, 2));
        }
    }
}
