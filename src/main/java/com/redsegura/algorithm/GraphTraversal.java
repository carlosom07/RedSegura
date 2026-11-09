package com.redsegura.algorithm;

import com.redsegura.graph.MixedGraph;
import com.redsegura.model.Edge;
import com.redsegura.model.Vertex;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementacion manual de BFS y DFS para el grafo mixto RedSegura.
 */
public class GraphTraversal {

    private final MixedGraph graph;

    public GraphTraversal(MixedGraph graph) {
        if (graph == null) {
            throw new IllegalArgumentException(
                    "El grafo no puede ser null."
            );
        }

        this.graph = graph;
    }

    /**
     * Construye una lista de adyacencia temporal. Respeta la direccion de las
     * aristas y omite las bloqueadas.
     */
    private Map<String, List<Vertex>> buildAdjacency() {

        Map<String, List<Vertex>> adjacency = new LinkedHashMap<>();

        for (Vertex vertex : graph.getVertices()) {
            adjacency.put(vertex.getId(), new ArrayList<>());
        }

        for (Edge edge : graph.getEdges()) {

            if (graph.isEdgeBlocked(edge.getId())) {
                continue;
            }

            String source = edge.getSource().getId();
            String destination = edge.getDestination().getId();

            adjacency.get(source).add(edge.getDestination());

            if (!edge.isDirected()) {
                adjacency.get(destination).add(edge.getSource());
            }
        }

        return adjacency;
    }

    /**
     * BFS: recorrido por amplitud mediante una cola FIFO.
     */
    public List<Vertex> bfs(String startId) {

        validateStart(startId);

        Map<String, List<Vertex>> adjacency = buildAdjacency();

        List<Vertex> result = new ArrayList<>();
        Set<String> visited = new LinkedHashSet<>();
        Deque<Vertex> queue = new ArrayDeque<>();

        Vertex start = graph.getVertex(startId);

        visited.add(start.getId());
        queue.addLast(start);

        while (!queue.isEmpty()) {

            Vertex current = queue.removeFirst();
            result.add(current);

            for (Vertex neighbor : adjacency.get(current.getId())) {

                if (visited.add(neighbor.getId())) {
                    queue.addLast(neighbor);
                }
            }
        }

        return result;
    }

    /**
     * DFS: recorrido en profundidad mediante una pila LIFO.
     */
    public List<Vertex> dfs(String startId) {

        validateStart(startId);

        Map<String, List<Vertex>> adjacency = buildAdjacency();

        List<Vertex> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Deque<Vertex> stack = new ArrayDeque<>();

        stack.push(graph.getVertex(startId));

        while (!stack.isEmpty()) {

            Vertex current = stack.pop();

            if (!visited.add(current.getId())) {
                continue;
            }

            result.add(current);

            List<Vertex> neighbors = adjacency.get(current.getId());

            // Se insertan en orden inverso para conservar
            // el orden original de las conexiones.
            for (int i = neighbors.size() - 1; i >= 0; i--) {

                Vertex neighbor = neighbors.get(i);

                if (!visited.contains(neighbor.getId())) {
                    stack.push(neighbor);
                }
            }
        }

        return result;
    }

    /**
     * Valida el identificador inicial.
     */
    private void validateStart(String startId) {

        if (startId == null || startId.isBlank()) {
            throw new IllegalArgumentException(
                    "El identificador inicial es obligatorio."
            );
        }

        if (!graph.containsVertex(startId)) {
            throw new IllegalArgumentException(
                    "El vertice inicial no existe: " + startId
            );
        }
    }
}
