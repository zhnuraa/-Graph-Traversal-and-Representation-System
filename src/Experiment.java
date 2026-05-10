import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Experiment {
    public static void runTraversals(Graph g, int start, boolean printOrder) {
        // BFS
        long startTime = System.nanoTime();
        List<Integer> bfsOrder = g.bfs(start);
        long endTime = System.nanoTime();
        long bfsTime = endTime - startTime;
        // DFS
        startTime = System.nanoTime();
        List<Integer> dfsOrder = g.dfs(start);
        endTime = System.nanoTime();
        long dfsTime = endTime - startTime;
        if (printOrder) {
            System.out.println("BFS order: " + bfsOrder);
            System.out.println("DFS order: " + dfsOrder);
        }
        System.out.println("BFS time: " + bfsTime + " ns");
        System.out.println("DFS time: " + dfsTime + " ns");
    }

    public static void runMultipleTests() {
        int[] sizes = {10, 30, 100};
        for (int size : sizes) {
            System.out.println("\n=== Graph size: " + size + " vertices ===");
            Graph g = createConnectedGraph(size, 2.0); // average degree ~2-3
            boolean printOrder = (size == 10);
            runTraversals(g, 0, printOrder);
        }
    }

    private static Graph createConnectedGraph(int numVertices, double avgDegree) {
        Graph g = new Graph(false); // undirected
        // add vertices
        for (int i = 0; i < numVertices; i++) {
            g.addVertex(new Vertex(i));
        }
        // ensure connectivity: simple path
        for (int i = 0; i < numVertices - 1; i++) {
            g.addEdge(i, i + 1);
        }
        // add extra random edges to reach avgDegree (approximately)
        int targetEdges = (int)(numVertices * avgDegree / 2);
        Random rand = ThreadLocalRandom.current();
        int edgesAdded = numVertices - 1;
        while (edgesAdded < targetEdges) {
            int u = rand.nextInt(numVertices);
            int v = rand.nextInt(numVertices);
            if (u != v && !g.hasEdge(u, v)) {
                g.addEdge(u, v);
                edgesAdded++;
            }
        }
        return g;
    }
}
