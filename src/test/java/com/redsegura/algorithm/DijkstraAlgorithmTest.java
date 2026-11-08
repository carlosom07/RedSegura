package com.redsegura.algorithm;

import com.redsegura.graph.MixedGraph;
import com.redsegura.model.Vertex;
import com.redsegura.model.Edge;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DijkstraAlgorithmTest {

    private MixedGraph graph;
    private DijkstraAlgorithm dijkstra;

    @BeforeEach
    void setUp() {

        graph = new MixedGraph();

        Vertex s01 = new Vertex(
                "S01", "UAJS Sede C", "Universidad"
        );

        Vertex s02 = new Vertex(
                "S02", "UAJS Sede E", "Universidad"
        );

        Vertex s07 = new Vertex(
                "S07", "Estacion Central SETP", "Transporte"
        );

        Vertex s08 = new Vertex(
                "S08", "Terminal de Transporte", "Transporte"
        );

        Vertex s09 = new Vertex(
                "S09", "Parque Santander", "Encuentro"
        );

        Vertex s10 = new Vertex(
                "S10", "Mercado de Sincelejo", "Comercio"
        );

        graph.addVertex(s01);
        graph.addVertex(s02);
        graph.addVertex(s07);
        graph.addVertex(s08);
        graph.addVertex(s09);
        graph.addVertex(s10);

        graph.addEdge(new Edge(
                "R04", s02, s01, 8, false,
                "Conexion entre sedes"
        ));

        graph.addEdge(new Edge(
                "R01", s01, s07, 4, true,
                "Corredor universitario"
        ));

        graph.addEdge(new Edge(
                "R16", s07, s08, 12, true,
                "Ruta SETP al terminal"
        ));

        graph.addEdge(new Edge(
                "R03", s01, s09, 5, false,
                "Corredor peatonal"
        ));

        graph.addEdge(new Edge(
                "R08", s09, s10, 3, false,
                "Centro historico"
        ));

        graph.addEdge(new Edge(
                "R09", s10, s08, 8, true,
                "Salida al terminal"
        ));

        dijkstra = new DijkstraAlgorithm(graph);
    }

    @Test
    void testOriginalShortestPath() {

        DijkstraAlgorithm.PathResult result
                = dijkstra.findShortestPath("S02", "S08");

        assertTrue(result.isReachable());
        assertEquals(24, result.getDistance());

        List<Vertex> path = result.getPath();

        assertEquals(4, path.size());
        assertEquals("S02", path.get(0).getId());
        assertEquals("S01", path.get(1).getId());
        assertEquals("S07", path.get(2).getId());
        assertEquals("S08", path.get(3).getId());
    }

    @Test
    void testAlternativePathWhenR16IsBlocked() {

        graph.blockEdge("R16");

        DijkstraAlgorithm.PathResult result
                = dijkstra.findShortestPath("S02", "S08");

        assertTrue(result.isReachable());
        assertEquals(24, result.getDistance());

        List<Vertex> path = result.getPath();

        assertEquals(5, path.size());
        assertEquals("S02", path.get(0).getId());
        assertEquals("S01", path.get(1).getId());
        assertEquals("S09", path.get(2).getId());
        assertEquals("S10", path.get(3).getId());
        assertEquals("S08", path.get(4).getId());
    }

    @Test
    void testNoAvailableRoute() {

        graph.blockEdge("R16");
        graph.blockEdge("R03");
        graph.blockEdge("R04");

        DijkstraAlgorithm.PathResult result
                = dijkstra.findShortestPath("S02", "S08");

        assertFalse(result.isReachable());
        assertTrue(result.getPath().isEmpty());
    }

    @Test
    void testSameOriginAndDestination() {

        DijkstraAlgorithm.PathResult result
                = dijkstra.findShortestPath("S02", "S02");

        assertTrue(result.isReachable());
        assertEquals(0, result.getDistance());
        assertEquals(1, result.getPath().size());
    }

    @Test
    void testNonexistentOrigin() {

        assertThrows(IllegalArgumentException.class,
                () -> dijkstra.findShortestPath("S99", "S08"));
    }

    @Test
    void testNonexistentDestination() {

        assertThrows(IllegalArgumentException.class,
                () -> dijkstra.findShortestPath("S02", "S99"));
    }

    @Test
    void testInvalidIdentifiers() {

        assertThrows(IllegalArgumentException.class,
                () -> dijkstra.findShortestPath(null, "S08"));

        assertThrows(IllegalArgumentException.class,
                () -> dijkstra.findShortestPath("S02", ""));
    }

    @Test
    void testNullGraph() {

        assertThrows(IllegalArgumentException.class,
                () -> new DijkstraAlgorithm(null));
    }
}
