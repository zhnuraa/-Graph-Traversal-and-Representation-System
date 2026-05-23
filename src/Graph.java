import java.util.*;

public class Graph {
    private Map<Integer, List<Edge>> adjList;   // now stores Edge objects
    private boolean directed;

    public Graph(boolean directed) {
        this.adjList = new HashMap<>();
        this.directed = directed;
    }

    // Add vertex (unchanged)
    public void addVertex(Vertex v) {
        adjList.putIfAbsent(v.getId(), new ArrayList<>());
    }

    // Unweighted edge (default weight = 1) – keeps BFS/DFS working
    public void addEdge(int from, int to) {
        addEdge(from, to, 1);
    }

    // Weighted edge (new for Dijkstra)
    public void addEdge(int from, int to, int weight) {
        adjList.putIfAbsent(from, new ArrayList<>());
        adjList.putIfAbsent(to, new ArrayList<>());
        adjList.get(from).add(new Edge(from, to, weight));
        if (!directed) {
            adjList.get(to).add(new Edge(to, from, weight));
        }
    }

    // Print graph with weights
    public void printGraph() {
        for (var entry : adjList.entrySet()) {
            System.out.print(entry.getKey() + " -> ");
            for (Edge e : entry.getValue()) {
                System.out.print(e.getDestination() + "(" + e.getWeight() + ") ");
            }
            System.out.println();
        }
    }

    // ---------- BFS and DFS remain the same (they ignore weights) ----------
    // We need helper to get neighbor IDs from edges
    private List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        for (Edge e : adjList.getOrDefault(vertex, Collections.emptyList())) {
            neighbors.add(e.getDestination());
        }
        return neighbors;
    }

    public List<Integer> bfsOrder(int start) {
        if (!adjList.containsKey(start)) return Collections.emptyList();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        List<Integer> order = new ArrayList<>();
        queue.add(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            order.add(vertex);
            for (int neighbor : getNeighbors(vertex)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return order;
    }

    public void bfs(int start) {
        System.out.println("BFS order: " + bfsOrder(start));
    }

    public List<Integer> dfsOrder(int start) {
        if (!adjList.containsKey(start)) return Collections.emptyList();
        Set<Integer> visited = new HashSet<>();
        Stack<Integer> stack = new Stack<>();
        List<Integer> order = new ArrayList<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            int vertex = stack.pop();
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                order.add(vertex);
                for (int neighbor : getNeighbors(vertex)) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
        return order;
    }

    public void dfs(int start) {
        System.out.println("DFS order: " + dfsOrder(start));
    }

    // ---------- DIJKSTRA'S ALGORITHM (simple O(V^2) version) ----------
    public void dijkstra(int start) {
        if (!adjList.containsKey(start)) {
            System.out.println("Start vertex not found!");
            return;
        }

        int V = adjList.size();
        // Get all vertex IDs
        List<Integer> vertices = new ArrayList<>(adjList.keySet());
        // Map vertex ID to index for array access
        Map<Integer, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < vertices.size(); i++) {
            indexMap.put(vertices.get(i), i);
        }

        int[] dist = new int[V];
        boolean[] visited = new boolean[V];
        int[] prev = new int[V];  // to store previous vertex for path reconstruction
