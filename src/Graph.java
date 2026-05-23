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
    // Initialize distances: infinity, except start = 0
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[indexMap.get(start)] = 0;

        // Main loop: find unvisited vertex with smallest distance
        for (int i = 0; i < V; i++) {
            int u = -1;
            int minDist = Integer.MAX_VALUE;
            for (int j = 0; j < V; j++) {
                if (!visited[j] && dist[j] < minDist) {
                    minDist = dist[j];
                    u = j;
                }
            }
            if (u == -1) break; // all remaining are unreachable

            visited[u] = true;
            int vertexId = vertices.get(u);

            // Relax all edges from u
            for (Edge e : adjList.getOrDefault(vertexId, Collections.emptyList())) {
                int v = indexMap.get(e.getDestination());
                int weight = e.getWeight();
                if (!visited[v] && dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    prev[v] = u;
                }
            }
        }

        // Print results
        System.out.println("\nDijkstra's Algorithm from vertex " + start + ":");
        for (int i = 0; i < V; i++) {
            int vertexId = vertices.get(i);
            if (dist[i] == Integer.MAX_VALUE) {
                System.out.println("  → " + vertexId + " : unreachable");
            } else {
                System.out.println("  → " + vertexId + " : distance = " + dist[i]);
                // Optional: print path
                System.out.print("      Path: " + vertexId);
                int p = i;
                while (prev[p] != -1) {
                    p = prev[p];
                    System.out.print(" <- " + vertices.get(p));
                }
                System.out.println();
            }
        }
    }
}
