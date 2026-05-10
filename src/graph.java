import java.util.*;

public class Graph {
    private Map<Integer, List<Integer>> adjList;
    private boolean directed;

    public Graph(boolean directed) {
        this.adjList = new HashMap<>();
        this.directed = directed;
    }

    public void addVertex(Vertex v) {
        adjList.putIfAbsent(v.getId(), new ArrayList<>());
    }

    public void addEdge(int from, int to) {
        adjList.putIfAbsent(from, new ArrayList<>());
        adjList.putIfAbsent(to, new ArrayList<>());
        adjList.get(from).add(to);
        if (!directed) {
            adjList.get(to).add(from);
        }
    }

    public void printGraph() {
        for (var entry : adjList.entrySet()) {
            System.out.print(entry.getKey() + " -> ");
            for (int neighbor : entry.getValue()) {
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }
    }

    public List<Integer> bfs(int start) {
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

    public List<Integer> dfs(int start) {
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
                // push neighbors in reverse order to maintain order similar to recursion? not required
                for (int neighbor : adjList.get(vertex)) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
        return order;
    }
}
