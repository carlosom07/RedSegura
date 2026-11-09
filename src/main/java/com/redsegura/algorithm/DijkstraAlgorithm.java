package com.redsegura.algorithm;

import com.redsegura.graph.MixedGraph;
import com.redsegura.model.Edge;
import com.redsegura.model.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Implementacion manual del algoritmo de Dijkstra. Utiliza una lista de
 * adyacencia y PriorityQueue.
 */
public class DijkstraAlgorithm {

    private final MixedGraph graph;

    public DijkstraAlgorithm(MixedGraph graph) {
        if (graph == null) {
            throw new IllegalArgumentException(
                    "El grafo no puede ser null."
            );
        }

        this.graph = graph;
    }

    /**
     * Representa una conexion hacia un vertice vecino.
     */
    private static class Neighbor {

        private final String vertexId;
        private final int weight;

        Neighbor(String vertexId, int weight) {
            this.vertexId = vertexId;
            this.weight = weight;
        }
    }

    /**
     * Representa una ruta y su costo total.
     */
    public static class PathResult {

        private final List<Vertex> path;
        private final int distance;

        public PathResult(List<Vertex> path, int distance) {
            this.path = new ArrayList<>(path);
            this.distance = distance;
        }

        public List<Vertex> getPath() {
            return Collections.unmodifiableList(path);
        }

        public int getDistance() {
            return distance;
        }

        public boolean isReachable() {
            return !path.isEmpty();
        }

        @Override
        public String toString() {

            if (!isReachable()) {
                return "No existe una ruta disponible.";
            }

            StringBuilder result = new StringBuilder();

            for (int i = 0; i < path.size(); i++) {

                if (i > 0) {
                    result.append(" -> ");
                }

                result.append(path.get(i).getId());
            }

            result.append(" | Costo total: ")
                    .append(distance);

            return result.toString();
        }
    }

    /**
     * Representa un elemento de la cola de prioridad.
     */
    private static class QueueNode {

        private final String vertexId;
        private final int distance;

        QueueNode(String vertexId, int distance) {
            this.vertexId = vertexId;
            this.distance = distance;
        }
    }

    /**
     * Construye una lista de adyacencia con las conexiones disponibles.
     */
    private Map<String, List<Neighbor>> buildAdjacency() {

        Map<String, List<Neighbor>> adjacency = new HashMap<>();

        for (Vertex vertex : graph.getVertices()) {
            adjacency.put(vertex.getId(), new ArrayList<>());
        }

        for (Edge edge : graph.getEdges()) {

            if (graph.isEdgeBlocked(edge.getId())) {
                continue;
            }

            String source = edge.getSource().getId();
            String destination = edge.getDestination().getId();

            adjacency.get(source).add(
                    new Neighbor(destination, edge.getWeight())
            );

            if (!edge.isDirected()) {
                adjacency.get(destination).add(
                        new Neighbor(source, edge.getWeight())
                );
            }
        }

        return adjacency;
    }

    /**
     * Calcula la ruta de menor costo entre dos vertices.
     */
    public PathResult findShortestPath(
            String startId, String destinationId
    ) {

        validateVertex(startId);
        validateVertex(destinationId);

        Map<String, List<Neighbor>> adjacency = buildAdjacency();

        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();

        for (Vertex vertex : graph.getVertices()) {
            distances.put(vertex.getId(), Integer.MAX_VALUE);
        }

        PriorityQueue<QueueNode> queue
                = new PriorityQueue<>(
                        Comparator.comparingInt(node -> node.distance)
                );

        distances.put(startId, 0);
        queue.add(new QueueNode(startId, 0));

        while (!queue.isEmpty()) {

            QueueNode current = queue.poll();

            // Ignorar entradas antiguas de la cola.
            if (current.distance > distances.get(current.vertexId)) {
                continue;
            }

            // El destino ya tiene su menor distancia definitiva.
            if (current.vertexId.equals(destinationId)) {
                break;
            }

            for (Neighbor neighbor
                    : adjacency.get(current.vertexId)) {

                int newDistance
                        = current.distance + neighbor.weight;

                if (newDistance < distances.get(neighbor.vertexId)) {

                    distances.put(neighbor.vertexId, newDistance);

                    previous.put(
                            neighbor.vertexId,
                            current.vertexId
                    );

                    queue.add(
                            new QueueNode(
                                    neighbor.vertexId,
                                    newDistance
                            )
                    );
                }
            }
        }

        if (distances.get(destinationId) == Integer.MAX_VALUE) {

            return new PathResult(
                    Collections.emptyList(),
                    Integer.MAX_VALUE
            );
        }

        return buildPath(
                startId,
                destinationId,
                previous,
                distances.get(destinationId)
        );
    }

    /**
     * Reconstruye la ruta utilizando los vertices anteriores.
     */
    private PathResult buildPath(
            String startId,
            String destinationId,
            Map<String, String> previous,
            int distance
    ) {

        List<Vertex> path = new ArrayList<>();

        String currentId = destinationId;

        while (currentId != null) {

            path.add(graph.getVertex(currentId));

            if (currentId.equals(startId)) {
                break;
            }

            currentId = previous.get(currentId);
        }

        Collections.reverse(path);

        return new PathResult(path, distance);
    }

    /**
     * Valida que el vertice exista.
     */
    private void validateVertex(String id) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(
                    "El identificador no puede estar vacio."
            );
        }

        if (!graph.containsVertex(id)) {
            throw new IllegalArgumentException(
                    "El vertice no existe: " + id
            );
        }
    }
}
