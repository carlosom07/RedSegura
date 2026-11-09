
# Diagrama de clases y dependencias de RedSegura

```mermaid
classDiagram
direction TB

class RedSeguraApplication {
    +main(String[] args)
    -mostrarMenu()
    -buscarLugar()
    -demostrarRotaciones()
    -mostrarRepresentaciones()
    -ejecutarRecorridos()
}

class RedSeguraCore {
    -BinarySearchTree binarySearchTree
    -AvlTree avlTree
    -MixedGraph graph
    -GraphTraversal traversal
    -DijkstraAlgorithm dijkstra
}

class Vertex {
    -String id
    -String name
    -String category
}

class Edge {
    -String id
    -Vertex source
    -Vertex destination
    -int weight
    -boolean directed
}

class BinarySearchTree {
    +insert(Vertex vertex)
    +search(String name)
    +inOrder()
}

class AvlTree {
    +insert(Vertex vertex)
    +search(String name)
    +inOrder()
    +isBalanced()
}

class MixedGraph {
    +addVertex(Vertex vertex)
    +addEdge(Edge edge)
    +blockEdge(String id)
    +unblockEdge(String id)
    +adjacencyList()
    +adjacencyMatrix()
    +incidenceMatrix()
}

class GraphTraversal {
    +bfs(String startId)
    +dfs(String startId)
}

class DijkstraAlgorithm {
    +findShortestPath(String startId, String destinationId)
}

RedSeguraApplication --> RedSeguraCore
RedSeguraCore *-- BinarySearchTree
RedSeguraCore *-- AvlTree
RedSeguraCore *-- MixedGraph
RedSeguraCore *-- GraphTraversal
RedSeguraCore *-- DijkstraAlgorithm
BinarySearchTree --> Vertex
AvlTree --> Vertex
MixedGraph o-- Vertex
MixedGraph o-- Edge
Edge --> Vertex
GraphTraversal --> MixedGraph
DijkstraAlgorithm --> MixedGraph
```

## Explicación

- RedSeguraApplication: contiene el menú de consola.
- RedSeguraCore: integra las estructuras y algoritmos.
- Vertex: representa los lugares de la red.
- Edge: representa las conexiones entre lugares.
- BinarySearchTree: implementa el árbol binario de búsqueda.
- AvlTree: implementa el árbol autoequilibrado.
- MixedGraph: administra vértices, conexiones y representaciones.
- GraphTraversal: implementa BFS y DFS.
- DijkstraAlgorithm: calcula las rutas de menor costo.
