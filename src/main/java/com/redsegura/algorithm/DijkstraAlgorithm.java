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
 * Implementacion manual del algoritmo de Dijkstra. Utiliza PriorityQueue para
 * seleccionar el menor costo.
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
     * Calcula la ruta de menor costo entre dos vertices.
     */
    public PathResult findShortestPath(
            String startId, String destinationId
    ) {

        validateVertex(startId);
        validateVertex(destinationId);

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
            if (current.distance
                    > distances.get(current.vertexId)) {
                continue;
            }

            // Ya llegamos al destino con el menor costo.
            if (current.vertexId.equals(destinationId)) {
                break;
            }

            for (Edge edge : graph.getEdges()) {

                if (graph.isEdgeBlocked(edge.getId())) {
                    continue;
                }

                String neighborId = null;

                if (edge.getSource().getId()
                        .equals(current.vertexId)) {

                    neighborId = edge.getDestination().getId();

                } else if (!edge.isDirected()
                        && edge.getDestination().getId()
                                .equals(current.vertexId)) {

                    neighborId = edge.getSource().getId();
                }

                if (neighborId == null) {
                    continue;
                }

                int newDistance
                        = current.distance + edge.getWeight();

                if (newDistance < distances.get(neighborId)) {

                    distances.put(neighborId, newDistance);

                    previous.put(neighborId, current.vertexId);

                    queue.add(
                            new QueueNode(neighborId, newDistance)
                    );
                }
            }
        }

        if (distances.get(destinationId) == Integer.MAX_VALUE) {
            return new PathResult(
                    Collections.emptyList(), Integer.MAX_VALUE
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
