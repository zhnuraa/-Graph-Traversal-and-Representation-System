public class Main {
    public static void main(String[] args) {
        System.out.println("========== Original Assignment (BFS/DFS Performance) ==========");
        Experiment.runMultipleTests();

        System.out.println("\n========== Bonus: Dijkstra's Algorithm ==========");
        // Create a weighted graph
        Graph weightedGraph = new Graph(false); // undirected weighted graph

        // Add vertices 0..5
        for (int i = 0; i <= 5; i++) {
            weightedGraph.addVertex(new Vertex(i));
        }

        // Add weighted edges (undirected)
        weightedGraph.addEdge(0, 1, 4);
        weightedGraph.addEdge(0, 2, 2);
        weightedGraph.addEdge(1, 2, 1);
        weightedGraph.addEdge(1, 3, 5);
        weightedGraph.addEdge(2, 3, 8);
        weightedGraph.addEdge(2, 4, 10);
        weightedGraph.addEdge(3, 4, 2);
        weightedGraph.addEdge(3, 5, 6);
        weightedGraph.addEdge(4, 5, 3);

        System.out.println("Weighted Graph:");
        weightedGraph.printGraph();

        // Run Dijkstra from vertex 0
        weightedGraph.dijkstra(0);
    }
}
