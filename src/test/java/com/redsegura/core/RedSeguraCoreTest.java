package com.redsegura.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedSeguraCoreTest {

    @Test
    void testLoadNetwork() {

        RedSeguraCore core = new RedSeguraCore();

        assertEquals(12, core.getVertexCount());
        assertEquals(18, core.getEdgeCount());
    }

    @Test
    void testSearchHospital() {

        RedSeguraCore core = new RedSeguraCore();

        assertNotNull(
                core.searchBinarySearchTree("Hospital Universitario")
        );

        assertNotNull(
                core.searchAvlTree("Hospital Universitario")
        );
    }

    @Test
    void testOriginalRoute() {

        RedSeguraCore core = new RedSeguraCore();

        var result = core.getDijkstra()
                .findShortestPath("S02", "S08");

        assertEquals(24, result.getDistance());
    }

    @Test
    void testAlternativeRoute() {

        RedSeguraCore core = new RedSeguraCore();

        core.blockEdge("R16");

        var result = core.getDijkstra()
                .findShortestPath("S02", "S08");

        assertEquals(24, result.getDistance());
        assertEquals(5, result.getPath().size());
    }

    @Test
    void testBFSFromS02() {

        RedSeguraCore core = new RedSeguraCore();

        var result = core.getTraversal().bfs("S02");

        assertFalse(result.isEmpty());
        assertEquals("S02", result.get(0).getId());
    }

    @Test
    void testDFSFromS02() {

        RedSeguraCore core = new RedSeguraCore();

        var result = core.getTraversal().dfs("S02");

        assertFalse(result.isEmpty());
        assertEquals("S02", result.get(0).getId());
    }
}
