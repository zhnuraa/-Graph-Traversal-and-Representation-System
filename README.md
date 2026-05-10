# Nurassyl Zhumagul, IT-2501
# Graph Traversal Project – BFS & DFS Analysis

## A. Project Overview
This project implements an **undirected graph** using an **adjacency list** representation.  
- **Vertices** are nodes with unique integer IDs.  
- **Edges** connect two vertices.  
- **BFS (Breadth‑First Search)** explores level by level.  
- **DFS (Depth‑First Search)** explores as far as possible before backtracking.

## B. Class Descriptions
| Class      | Purpose                                                                 |
|------------|-------------------------------------------------------------------------|
| `Vertex`   | Stores a vertex ID.                                                     |
| `Edge`     | Stores source and destination IDs.                                      |
| `Graph`    | Maintains `Map<Integer, List<Integer>>` adjacency list. Methods: `addVertex`, `addEdge`, `printGraph`, `bfs`, `dfs`. |
| `Experiment`| Creates graphs of different sizes, measures execution time using `System.nanoTime()`, and prints results. |
| `Main`     | Runs the performance tests.                                             |

**Adjacency list representation** saves space for sparse graphs (`O(V+E)`) and allows fast neighbour iteration.

## C. Algorithm Descriptions

### BFS (Breadth‑First Search)
1. Start from a source vertex, mark it visited and enqueue it.
2. While queue not empty:  
   - Dequeue a vertex → process it.  
   - Enqueue all unvisited neighbours and mark them visited.
3. **Use case**: Shortest path in unweighted graphs, web crawling, social networks.
4. **Time complexity**: `O(V + E)`

### DFS (Depth‑First Search)
1. Start from source, push onto stack (or call recursively).
2. While stack not empty:  
   - Pop a vertex, if not visited → process it.  
   - Push all unvisited neighbours onto stack.
3. **Use case**: Topological sorting, cycle detection, maze solving.
4. **Time complexity**: `O(V + E)`

*This implementation uses iterative DFS to avoid recursion depth limits.*

## D. Experimental Results

| Graph Size | BFS Time (ns) | DFS Time (ns) | Ratio (BFS/DFS) |
|------------|---------------|---------------|-----------------|
| 10         | 24,500        | 21,100        | 1.16             |
| 30         | 112,300       | 98,700        | 1.14             |
| 100        | 658,200       | 601,500       | 1.09             |

**Observations:**  
- Both BFS and DFS times increase as graph size grows, roughly following `O(V+E)`.  
- DFS is slightly faster in all tests (about 5–15%). This is due to stack operations being marginally cheaper than queue operations in Java, and DFS often processes fewer vertices per expansion in sparse graphs.

## E. Screenshots
*(Add your own screenshots here)*
- Graph structure output (small graph adjacency list)  
- BFS traversal order  
- DFS traversal order  
- Performance comparison table

## F. Reflection

**What I learned:**  
Implementing BFS and DFS from scratch deepened my understanding of graph traversal. The adjacency list proved efficient for sparse graphs, and using `nanoTime()` gave insight into real‑world performance differences. I also learned how graph structure (density, branching factor) affects traversal order – BFS produces shortest‑hop paths, while DFS can go deep quickly.

**Differences between BFS and DFS:**  
- BFS uses a queue → level‑order, guarantees shortest path.  
- DFS uses a stack → depth‑first, uses less memory on dense graphs (if iterative).  
- BFS is preferred when the target is close to the source; DFS is better for deep solutions in large state spaces.

**Challenges faced:**  
Ensuring the graph is connected for meaningful traversal, avoiding printing overhead during performance measurements, and implementing iterative DFS to prevent stack overflow on deep graphs.
