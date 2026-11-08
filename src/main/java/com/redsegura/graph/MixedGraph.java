package com.redsegura.graph;

import com.redsegura.model.Vertex;
import com.redsegura.model.Edge;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Grafo mixto de RedSegura. Permite conexiones dirigidas y no dirigidas.
 */
public class MixedGraph {

    private final Map<String, Vertex> vertices;
    private final Map<String, Edge> edges;
    private final List<String> blockedEdges;

    public MixedGraph() {
        vertices = new LinkedHashMap<>();
        edges = new LinkedHashMap<>();
        blockedEdges = new ArrayList<>();
    }

    /**
     * Agrega un vertice al grafo.
     */
    public void addVertex(Vertex vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException(
                    "El vertice no puede ser null."
            );
        }

        if (vertices.containsKey(vertex.getId())) {
            throw new IllegalArgumentException(
                    "Ya existe un vertice con ese identificador."
            );
        }

        vertices.put(vertex.getId(), vertex);
    }

    /**
     * Agrega una conexion dirigida o no dirigida.
     */
    public void addEdge(Edge edge) {
        if (edge == null) {
            throw new IllegalArgumentException(
                    "La conexion no puede ser null."
            );
        }

        if (edges.containsKey(edge.getId())) {
            throw new IllegalArgumentException(
                    "Ya existe una conexion con ese identificador."
            );
        }

        if (!vertices.containsKey(edge.getSource().getId())
                || !vertices.containsKey(
                        edge.getDestination().getId())) {
            throw new IllegalArgumentException(
                    "Los vertices de la conexion deben existir."
            );
        }

        if (edge.getWeight() <= 0) {
            throw new IllegalArgumentException(
                    "El peso debe ser positivo."
            );
        }

        edges.put(edge.getId(), edge);
    }

    /**
     * Busca un vertice por identificador.
     */
    public Vertex getVertex(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(
                    "El identificador no puede estar vacio."
            );
        }

        return vertices.get(id.trim());
    }

    /**
     * Busca una conexion por identificador.
     */
    public Edge getEdge(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(
                    "El identificador no puede estar vacio."
            );
        }

        return edges.get(id.trim());
    }

    /**
     * Devuelve todos los vertices.
     */
    public List<Vertex> getVertices() {
        return new ArrayList<>(vertices.values());
    }

    /**
     * Devuelve todas las conexiones.
     */
    public List<Edge> getEdges() {
        return new ArrayList<>(edges.values());
    }

    /**
     * Bloquea una conexion sin eliminarla.
     */
    public void blockEdge(String edgeId) {
        if (!edges.containsKey(edgeId)) {
            throw new IllegalArgumentException(
                    "La conexion no existe."
            );
        }

        if (!blockedEdges.contains(edgeId)) {
            blockedEdges.add(edgeId);
        }
    }

    /**
     * Habilita nuevamente una conexion.
     */
    public void unblockEdge(String edgeId) {
        blockedEdges.remove(edgeId);
    }

    /**
     * Comprueba si una conexion esta bloqueada.
     */
    public boolean isEdgeBlocked(String edgeId) {
        return blockedEdges.contains(edgeId);
    }

    public int getVertexCount() {
        return vertices.size();
    }

    public int getEdgeCount() {
        return edges.size();
    }

    public int getBlockedEdgeCount() {
        return blockedEdges.size();
    }

    public boolean containsVertex(String id) {
        return vertices.containsKey(id);
    }

    public boolean containsEdge(String id) {
        return edges.containsKey(id);
    }

    public void clear() {
        vertices.clear();
        edges.clear();
        blockedEdges.clear();
    }
    
    
    /**
     * Lista de adyacencia.
     * Las conexiones dirigidas aparecen solo en su origen.
     * Las no dirigidas aparecen en ambos extremos.
     */
    public Map<String, List<String>> adjacencyList() {

        Map<String, List<String>> adjacency = new LinkedHashMap<>();

        for (String id : vertices.keySet()) {
            adjacency.put(id, new ArrayList<>());
        }

        for (Edge edge : edges.values()) {

            if (blockedEdges.contains(edge.getId())) {
                continue;
            }

            String source = edge.getSource().getId();
            String destination = edge.getDestination().getId();

            adjacency.get(source).add(
                destination + " (" + edge.getWeight() + ")"
            );

            if (!edge.isDirected()) {
                adjacency.get(destination).add(
                    source + " (" + edge.getWeight() + ")"
                );
            }
        }

        return adjacency;
    }

    /**
     * Matriz de adyacencia.
     * Un cero indica que no existe conexion.
     */
    public int[][] adjacencyMatrix() {

        List<String> ids = new ArrayList<>(vertices.keySet());
        int[][] matrix = new int[ids.size()][ids.size()];

        for (Edge edge : edges.values()) {

            if (blockedEdges.contains(edge.getId())) {
                continue;
            }

            int source = ids.indexOf(edge.getSource().getId());
            int destination = ids.indexOf(
                edge.getDestination().getId()
            );

            matrix[source][destination] = edge.getWeight();

            if (!edge.isDirected()) {
                matrix[destination][source] = edge.getWeight();
            }
        }

        return matrix;
    }

    /**
     * Matriz de incidencia.
     *
     * Arista dirigida:
     * -1 en el origen y +1 en el destino.
     *
     * Arista no dirigida:
     * +1 en ambos extremos.
     */
    public int[][] incidenceMatrix() {

        List<Vertex> vertexList = getVertices();
        List<Edge> edgeList = getEdges();

        int[][] matrix = new int[
            vertexList.size()
        ][
            edgeList.size()
        ];

        for (int j = 0; j < edgeList.size(); j++) {

            Edge edge = edgeList.get(j);

            int source = -1;
            int destination = -1;

            for (int i = 0; i < vertexList.size(); i++) {

                if (vertexList.get(i).getId()
                        .equals(edge.getSource().getId())) {
                    source = i;
                }

                if (vertexList.get(i).getId()
                        .equals(edge.getDestination().getId())) {
                    destination = i;
                }
            }

            if (blockedEdges.contains(edge.getId())) {
                continue;
            }

            if (edge.isDirected()) {
                matrix[source][j] = -1;
                matrix[destination][j] = 1;
            } else {
                matrix[source][j] = 1;
                matrix[destination][j] = 1;
            }
        }

        return matrix;
    }

    /**
     * Devuelve los identificadores en el orden de las matrices.
     */
    public List<String> getVertexIds() {
        return new ArrayList<>(vertices.keySet());
    }

    /**
     * Devuelve los identificadores de las conexiones
     * en el orden de las columnas de incidencia.
     */
    public List<String> getEdgeIds() {
        return new ArrayList<>(edges.keySet());
    }

}
