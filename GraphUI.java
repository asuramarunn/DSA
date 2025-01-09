import javax.swing.*;
import GraphDataStructure.*;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class GraphUI extends JFrame {
    private Graph graph;
    private Node selectedNode = null;
    private Point tempLineEnd = null;
    private JComboBox<String> algorithmSelector;
    private JButton runButton, clearGraphButton; // Removed deleteNodeButton
    private Node startNode = null, endNode = null;
    private JTextArea statusArea;
    private List<Node> currentPath = null;

    public GraphUI() {
        graph = new Graph();
        setTitle("Graph Builder");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Add graph panel
        GraphPanel panel = new GraphPanel();
        add(panel, BorderLayout.CENTER);

        // Add controls
        JPanel controls = new JPanel();
        controls.setLayout(new FlowLayout());

        algorithmSelector = new JComboBox<>(new String[] { "Dijkstra", "A*", "Greedy BFS" });
        controls.add(new JLabel("Algorithm:"));
        controls.add(algorithmSelector);

        runButton = new JButton("Run");
        runButton.addActionListener(e -> runAlgorithm());
        controls.add(runButton);

        clearGraphButton = new JButton("Clear Graph");
        clearGraphButton.addActionListener(e -> clearGraph());
        controls.add(clearGraphButton);

        add(controls, BorderLayout.NORTH);

        // Add status area
        statusArea = new JTextArea(8, 30);
        statusArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(statusArea);
        add(scrollPane, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void runAlgorithm() {
        if (startNode == null || endNode == null) {
            JOptionPane.showMessageDialog(this, "Please select start and end nodes.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Chỉ kiểm tra đồ thị khi cần thiết, ví dụ như khi debug
        if (isDebugMode()) {
            System.out.println("Cac nut trong do thi:");
            for (Node node : graph.getNodes()) {
                System.out.println(node);
            }

            System.out.println("Cac canh trong do thi:");
            for (Edge edge : graph.getEdges()) {
                System.out.println(edge);
            }
        }

        String algorithm = (String) algorithmSelector.getSelectedItem();
        List<Node> path = null;
        statusArea.setText(""); // Clear status area

        ShortestPathAlgorithms.EuclideanHeuristic heuristic = new ShortestPathAlgorithms.EuclideanHeuristic();

        switch (algorithm) {

            case "Dijkstra":
                // Dijkstra trả về Map<Node, Integer>
                Map<Node, Integer> dijkstraResult = ShortestPathAlgorithms.dijkstra(graph, startNode);
                path = reconstructPathDijkstra(dijkstraResult, startNode, endNode);
                statusArea.append("Dijkstra algorithm executed.\n");
                break;

            case "A*":
                // Tạo đối tượng heuristic (ở đây dùng EuclideanHeuristic)

                // Gọi hàm aStar với heuristic đã khai báo
                Map<Node, Node> aStarResult = ShortestPathAlgorithms.aStar(graph, startNode, endNode, heuristic);
                path = reconstructPath(aStarResult, startNode, endNode);
                System.out.println("Nut dau: " + startNode);
                System.out.println("Nut cuoi: " + endNode);
                statusArea.append("A* algorithm executed.\n");

                break;

            case "Greedy BFS":
                // Greedy BFS sử dụng heuristic và trả về Map<Node, Node>

                Map<Node, Node> greedyBFSResult = ShortestPathAlgorithms.greedyBFS(graph, startNode, endNode, heuristic);
                path = reconstructPath(greedyBFSResult, startNode, endNode);
                statusArea.append("Greedy BFS algorithm executed.\n");
                break;
        }

        if (path != null && !path.isEmpty()) {
            currentPath = path;
            repaint();
            statusArea.append("Path found: " + path + "\n");
        } else {
            currentPath = null;
            repaint();
            statusArea.append("No path found.\n");
            JOptionPane.showMessageDialog(this, "No path found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Hàm kiểm tra chế độ debug
    private boolean isDebugMode() {
        // Bạn có thể thay thế kiểm tra này bằng một flag hoặc một tùy chọn giao diện để
        // bật chế độ debug
        return true; // Đảm bảo luôn in debug
    }

    private void clearGraph() {
        graph.clear(); // Ensure this method exists in Graph class
        statusArea.append("Graph cleared.\n");
        selectedNode = null;
        startNode = null;
        endNode = null;
        currentPath = null;
        repaint();
    }

    private List<Node> reconstructPath(Map<Node, Node> result, Node start, Node goal) {
        List<Node> path = new ArrayList<>();
        Node current = goal;
        
        // Kiểm tra khi goal không phải là start.
        while (current != null && !current.equals(start)) {
            path.add(0, current);  // Thêm node vào đầu danh sách path
            current = result.get(current);  // Lấy node cha
        }
        
        // Nếu không có đường đi (path không bắt đầu từ start)
        if (current != null && current.equals(start)) {
            path.add(0, start);  // Thêm node start vào đầu đường đi
            System.out.println("Duong di: " + path);  // In ra đường đi
            return path;
        }
    
        System.out.println("Khong tim thay duong di.");
        return null;  // Không tìm thấy đường đi
    }
    
    

    private List<Node> reconstructPathDijkstra(Map<Node, Integer> result, Node start, Node goal) {
        List<Node> path = new ArrayList<>();
        Node current = goal;

        while (current != null && result.containsKey(current)) {
            path.add(0, current);
            // Find the previous node with the minimum distance
            Node previous = null;
            int minDistance = Integer.MAX_VALUE;
            for (Edge edge : graph.getEdges()) {
                if (edge.end.equals(current) && result.get(edge.start) < minDistance) {
                    minDistance = result.get(edge.start);
                    previous = edge.start;
                }
            }
            current = previous;
        }

        if (!path.isEmpty() && path.get(0).equals(start)) {
            return path;
        }

        return null;
    }

    private void deleteNode(Node node) {
        if (node != null) {
            graph.removeNode(node);
            statusArea.append("Node deleted: " + node.name + "\n");
            renumberNodes();
            selectedNode = null;
            startNode = null;
            endNode = null;
            currentPath = null;
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a node to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void renumberNodes() {
        List<Node> nodes = graph.getNodes();
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).name = "Node " + (i + 1);
        }
        statusArea.append("Nodes renumbered.\n");
    }

    private class GraphPanel extends JPanel {
        public GraphPanel() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        for (Node node : graph.getNodes()) {
                            if (isWithinNode(e.getPoint(), node)) {
                                deleteNode(node);
                                return;
                            }
                        }
                    } else {
                        for (Node node : graph.getNodes()) {
                            if (isWithinNode(e.getPoint(), node)) {
                                if (SwingUtilities.isLeftMouseButton(e)) {
                                    startNode = node;
                                    statusArea.append("Start node selected: " + node.name + "\n");
                                    selectedNode = node;
                                } else if (SwingUtilities.isRightMouseButton(e)) {
                                    endNode = node;
                                    statusArea.append("End node selected: " + node.name + "\n");
                                }
                                repaint();
                                return;
                            }
                        }

                        String nodeName = "Node " + (graph.getNodes().size() + 1);
                        graph.addNode(new Node(nodeName, e.getX(), e.getY()));
                        statusArea.append("New node created: " + nodeName + "\n");
                        repaint();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (selectedNode != null) {
                        for (Node node : graph.getNodes()) {
                            if (isWithinNode(e.getPoint(), node) && !node.equals(selectedNode)) {
                                String weightStr = JOptionPane.showInputDialog("Enter weight:");
                                try {
                                    int weight = Integer.parseInt(weightStr);
                                    graph.addEdge(selectedNode, node, weight);
                                    statusArea.append("Edge created between " + selectedNode.name + " and " + node.name
                                            + " with weight " + weight + "\n");
                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(null, "Invalid weight value!", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                            }
                        }
                    }
                    selectedNode = null;
                    tempLineEnd = null;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (Edge edge : graph.getEdges()) {
                if (currentPath != null && currentPath.contains(edge.start) && currentPath.contains(edge.end)) {
                    g.setColor(Color.RED);
                    ((Graphics2D) g).setStroke(new BasicStroke(3));
                } else {
                    g.setColor(Color.GRAY);
                    ((Graphics2D) g).setStroke(new BasicStroke(1));
                }
                g.drawLine(edge.start.x, edge.start.y, edge.end.x, edge.end.y);

                g.setColor(Color.BLACK);
                int midX = (edge.start.x + edge.end.x) / 2;
                int midY = (edge.start.y + edge.end.y) / 2;
                g.drawString(String.valueOf(edge.weight), midX, midY);
            }

            for (Node node : graph.getNodes()) {
                if (node.equals(startNode)) {
                    g.setColor(Color.GREEN);
                } else if (node.equals(endNode)) {
                    g.setColor(Color.RED);
                } else if (node.equals(selectedNode)) {
                    g.setColor(Color.BLUE);
                } else if (currentPath != null && currentPath.contains(node)) {
                    g.setColor(Color.ORANGE);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillOval(node.x - 15, node.y - 15, 30, 30);

                g.setColor(Color.WHITE);
                g.drawString(node.name, node.x - 10, node.y + 5);

                if (node.equals(selectedNode)) {
                    ((Graphics2D) g).setStroke(new BasicStroke(2));
                    g.setColor(Color.YELLOW);
                    g.drawOval(node.x - 18, node.y - 18, 36, 36);
                }
            }
        }

        private boolean isWithinNode(Point p, Node node) {
            int dx = p.x - node.x;
            int dy = p.y - node.y;
            return dx * dx + dy * dy <= 15 * 15;

        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GraphUI::new);
    }
}
