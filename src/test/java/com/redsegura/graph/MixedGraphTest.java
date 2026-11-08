package com.redsegura.graph;

import com.redsegura.model.Vertex;
import com.redsegura.model.Edge;
import java.util.Map;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MixedGraphTest {

    @Test
    void testAddVerticesAndEdges() {

        MixedGraph graph = new MixedGraph();

        Vertex a = new Vertex("S01", "Universidad", "Educacion");
        Vertex b = new Vertex("S02", "Hospital", "Salud");

        graph.addVertex(a);
        graph.addVertex(b);

        graph.addEdge(new Edge(
                "R01", a, b, 5, true, "Ruta dirigida"
        ));

        assertEquals(2, graph.getVertexCount());
        assertEquals(1, graph.getEdgeCount());
    }

    @Test
    void testDirectedAdjacency() {

        MixedGraph graph = new MixedGraph();

        Vertex a = new Vertex("S01", "Universidad", "Educacion");
        Vertex b = new Vertex("S02", "Hospital", "Salud");

        graph.addVertex(a);
        graph.addVertex(b);

        graph.addEdge(new Edge(
                "R01", a, b, 5, true, "Ruta dirigida"
        ));

        Map<String, List<String>> adjacency
                = graph.adjacencyList();

        assertEquals(1, adjacency.get("S01").size());
        assertEquals(0, adjacency.get("S02").size());
    }

    @Test
    void testUndirectedAdjacency() {

        MixedGraph graph = new MixedGraph();

        Vertex a = new Vertex("S01", "Universidad", "Educacion");
        Vertex b = new Vertex("S02", "Hospital", "Salud");

        graph.addVertex(a);
        graph.addVertex(b);

        graph.addEdge(new Edge(
                "R01", a, b, 5, false, "Ruta no dirigida"
        ));

        Map<String, List<String>> adjacency
                = graph.adjacencyList();

        assertEquals(1, adjacency.get("S01").size());
        assertEquals(1, adjacency.get("S02").size());
    }

    @Test
    void testAdjacencyMatrix() {

        MixedGraph graph = new MixedGraph();

        Vertex a = new Vertex("S01", "Universidad", "Educacion");
        Vertex b = new Vertex("S02", "Hospital", "Salud");

        graph.addVertex(a);
        graph.addVertex(b);

        graph.addEdge(new Edge(
                "R01", a, b, 5, false, "Ruta"
        ));

        int[][] matrix = graph.adjacencyMatrix();

        assertEquals(5, matrix[0][1]);
        assertEquals(5, matrix[1][0]);
    }

    @Test
    void testIncidenceMatrixDirectedEdge() {

        MixedGraph graph = new MixedGraph();

        Vertex a = new Vertex("S01", "Universidad", "Educacion");
        Vertex b = new Vertex("S02", "Hospital", "Salud");

        graph.addVertex(a);
        graph.addVertex(b);

        graph.addEdge(new Edge(
                "R01", a, b, 5, true, "Ruta"
        ));

        int[][] matrix = graph.incidenceMatrix();

        assertEquals(-1, matrix[0][0]);
        assertEquals(1, matrix[1][0]);
    }

    @Test
    void testBlockAndUnblockEdge() {

        MixedGraph graph = new MixedGraph();

        Vertex a = new Vertex("S01", "Universidad", "Educacion");
        Vertex b = new Vertex("S02", "Hospital", "Salud");

        graph.addVertex(a);
        graph.addVertex(b);

        graph.addEdge(new Edge(
                "R01", a, b, 5, true, "Ruta"
        ));

        graph.blockEdge("R01");

        assertTrue(graph.isEdgeBlocked("R01"));
        assertEquals(0, graph.adjacencyMatrix()[0][1]);

        graph.unblockEdge("R01");

        assertFalse(graph.isEdgeBlocked("R01"));
        assertEquals(5, graph.adjacencyMatrix()[0][1]);
    }

    @Test
    void testRejectInvalidEdge() {

        MixedGraph graph = new MixedGraph();

        Vertex a = new Vertex("S01", "Universidad", "Educacion");
        Vertex b = new Vertex("S02", "Hospital", "Salud");

        graph.addVertex(a);

        Edge edge = new Edge(
                "R01", a, b, 5, true, "Ruta"
        );

        assertThrows(IllegalArgumentException.class,
                () -> graph.addEdge(edge));
    }
}
