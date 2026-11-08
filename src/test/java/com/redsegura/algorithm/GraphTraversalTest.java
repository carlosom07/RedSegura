package com.redsegura.algorithm;

import com.redsegura.graph.MixedGraph;
import com.redsegura.model.Vertex;
import com.redsegura.model.Edge;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphTraversalTest {

    private MixedGraph graph;
    private GraphTraversal traversal;

    private Vertex s01;
    private Vertex s02;
    private Vertex s03;
    private Vertex s04;

    @BeforeEach
    void setUp() {

        graph = new MixedGraph();

        s01 = new Vertex("S01", "Universidad", "Educacion");
        s02 = new Vertex("S02", "Hospital", "Salud");
        s03 = new Vertex("S03", "Terminal", "Transporte");
        s04 = new Vertex("S04", "Clinica", "Salud");

        graph.addVertex(s01);
        graph.addVertex(s02);
        graph.addVertex(s03);
        graph.addVertex(s04);

        graph.addEdge(new Edge(
                "R01", s01, s02, 5, false, "Conexion no dirigida"
        ));

        graph.addEdge(new Edge(
                "R02", s02, s03, 4, true, "Conexion dirigida"
        ));

        graph.addEdge(new Edge(
                "R03", s01, s04, 3, true, "Conexion dirigida"
        ));

        traversal = new GraphTraversal(graph);
    }

    @Test
    void testBFS() {

        List<Vertex> result = traversal.bfs("S01");

        assertEquals(4, result.size());

        assertEquals("S01", result.get(0).getId());
        assertEquals("S02", result.get(1).getId());
        assertEquals("S04", result.get(2).getId());
        assertEquals("S03", result.get(3).getId());
    }

    @Test
    void testDFS() {

        List<Vertex> result = traversal.dfs("S01");

        assertEquals(4, result.size());

        assertEquals("S01", result.get(0).getId());
        assertEquals("S02", result.get(1).getId());
        assertEquals("S03", result.get(2).getId());
        assertEquals("S04", result.get(3).getId());
    }

    @Test
    void testDirectedEdge() {

        List<Vertex> result = traversal.bfs("S03");

        assertEquals(1, result.size());
        assertEquals("S03", result.get(0).getId());
    }

    @Test
    void testUndirectedEdge() {

        List<Vertex> result = traversal.bfs("S02");

        assertEquals(4, result.size());
    }

    @Test
    void testBlockedEdge() {

        graph.blockEdge("R01");

        List<Vertex> result = traversal.bfs("S01");

        assertEquals(2, result.size());
        assertEquals("S01", result.get(0).getId());
        assertEquals("S04", result.get(1).getId());
    }

    @Test
    void testNonexistentStartVertex() {

        assertThrows(IllegalArgumentException.class,
                () -> traversal.bfs("S99"));

        assertThrows(IllegalArgumentException.class,
                () -> traversal.dfs("S99"));
    }

    @Test
    void testInvalidStartVertex() {

        assertThrows(IllegalArgumentException.class,
                () -> traversal.bfs(null));

        assertThrows(IllegalArgumentException.class,
                () -> traversal.dfs(""));
    }

    @Test
    void testNullGraph() {

        assertThrows(IllegalArgumentException.class,
                () -> new GraphTraversal(null));
    }
}
