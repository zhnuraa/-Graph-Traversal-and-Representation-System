import java.util.*;

public class Graph {
    private Map<Integer, List<Integer>> adjList;
    private boolean directed;

    public Graph(boolean directed) {
        this.adjList = new HashMap<>();
        this.directed = directed;
    }

    // Add vertex (required method)
    public void addVertex(Vertex v) {
        adjList.putIfAbsent(v.getId(), new ArrayList<>());
    }

    // Add edge (required method)
    public void addEdge(int from, int to) {
        adjList.putIfAbsent(from, new ArrayList<>());
        adjList.putIfAbsent(to, new ArrayList<>());
        adjList.get(from).add(to);
        if (!directed) {
            adjList.get(to).add(from);   // undirected
        }
    }

    // Print adjacency list (required method)
    public void printGraph() {
        for (var entry : adjList.entrySet()) {
            System.out.print(entry.getKey() + " -> ");
            for (int neighbor : entry.getValue()) {
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }
    }

    // ---------- BFS (required void method that prints order) ----------
    public void bfs(int start) {
        List<Integer> order = bfsOrder(start);
        System.out.println("BFS order: " + order);
    }

    // Returns BFS order without printing (for performance testing)
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
            for (int neighbor : adjList.get(vertex)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return order;
    }

    // ---------- DFS (required void method that prints order) ----------
    public void dfs(int start) {
        List<Integer> order = dfsOrder(start);
        System.out.println("DFS order: " + order);
    }

    // Returns DFS order without printing (iterative to avoid recursion depth issues)
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
                for (int neighbor : adjList.get(vertex)) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
        return order;
    }

    // Helper for edge existence (used in graph generation)
    public boolean hasEdge(int from, int to) {
        return adjList.containsKey(from) && adjList.get(from).contains(to);
    }
}
