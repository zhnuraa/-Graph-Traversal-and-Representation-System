import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Experiment {

    // Runs BFS/DFS on a graph and measures time (prints order only for small graph)
    public static void runTraversals(Graph g, int start, boolean printOrder) {
        // BFS timing
        long startTime = System.nanoTime();
        List<Integer> bfsOrder = g.bfsOrder(start);
        long endTime = System.nanoTime();
        long bfsTime = endTime - startTime;

        // DFS timing
        startTime = System.nanoTime();
        List<Integer> dfsOrder = g.dfsOrder(start);
        endTime = System.nanoTime();
        long dfsTime = endTime - startTime;

        if (printOrder) {
            System.out.println("BFS order: " + bfsOrder);
            System.out.println("DFS order: " + dfsOrder);
        }
        System.out.println("BFS time: " + bfsTime + " ns");
        System.out.println("DFS time: " + dfsTime + " ns");
    }

    // Creates graphs of sizes 10, 30, 100 and runs experiments
    public static void runMultipleTests() {
        int[] sizes = {10, 30, 100};
        for (int size : sizes) {
            System.out.println("\n=== Graph size: " + size + " vertices ===");
            // Create connected graph with average degree ~2.5
            Graph g = createConnectedGraph(size, 2.5);
            // Print graph structure only for small size (optional)
            if (size == 10) {
                System.out.println("Adjacency List:");
                g.printGraph();
            }
            boolean printOrder = (size == 10);
            runTraversals(g, 0, printOrder);
        }
    }

    // Generates a connected undirected graph with a given average degree
    private static Graph createConnectedGraph(int numVertices, double avgDegree) {
        Graph g = new Graph(false); // undirected
        // Add all vertices
        for (int i = 0; i < numVertices; i++) {
            g.addVertex(new Vertex(i));
        }
        // Ensure connectivity: a simple path 0-1-2-...-(n-1)
        for (int i = 0; i < numVertices - 1; i++) {
            g.addEdge(i, i + 1);
        }
        // Add extra random edges to reach target average degree
        int targetEdges = (int) (numVertices * avgDegree / 2);
        Random rand = ThreadLocalRandom.current();
        int edgesAdded = numVertices - 1;  // from the path
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
