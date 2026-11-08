package com.redsegura.tree;

import com.redsegura.model.Vertex;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AvlTreeTest {

    private AvlTree tree;

    @BeforeEach
    void setUp() {
        tree = new AvlTree();
    }

    private Vertex createVertex(String id, String name) {
        return new Vertex(id, name, "Prueba");
    }

    @Test
    void testInsertAndSearch() {
        Vertex vertex = createVertex("S05",
                "Hospital Universitario");

        tree.insert(vertex);

        assertEquals(vertex,
                tree.search("Hospital Universitario"));
        assertEquals(1, tree.size());
    }

    @Test
    void testLLRotation() {
        tree.insert(createVertex("S01", "30"));
        tree.insert(createVertex("S02", "20"));
        tree.insert(createVertex("S03", "10"));

        assertEquals("20", tree.getRoot().getName());
        assertTrue(tree.isBalanced());
    }

    @Test
    void testRRRotation() {
        tree.insert(createVertex("S01", "10"));
        tree.insert(createVertex("S02", "20"));
        tree.insert(createVertex("S03", "30"));

        assertEquals("20", tree.getRoot().getName());
        assertTrue(tree.isBalanced());
    }

    @Test
    void testLRRotation() {
        tree.insert(createVertex("S01", "30"));
        tree.insert(createVertex("S02", "10"));
        tree.insert(createVertex("S03", "20"));

        assertEquals("20", tree.getRoot().getName());
        assertTrue(tree.isBalanced());
    }

    @Test
    void testRLRotation() {
        tree.insert(createVertex("S01", "10"));
        tree.insert(createVertex("S02", "30"));
        tree.insert(createVertex("S03", "20"));

        assertEquals("20", tree.getRoot().getName());
        assertTrue(tree.isBalanced());
    }

    @Test
    void testInOrderTraversal() {
        tree.insert(createVertex("S01", "30"));
        tree.insert(createVertex("S02", "10"));
        tree.insert(createVertex("S03", "20"));

        List<Vertex> result = tree.inOrder();

        assertEquals(3, result.size());
        assertEquals("10", result.get(0).getName());
        assertEquals("20", result.get(1).getName());
        assertEquals("30", result.get(2).getName());
    }

    @Test
    void testRejectNullVertex() {
        assertThrows(IllegalArgumentException.class,
                () -> tree.insert(null));
    }

    @Test
    void testRejectDuplicateNames() {
        tree.insert(createVertex("S01", "Hospital"));

        assertThrows(IllegalArgumentException.class,
                () -> tree.insert(
                        createVertex("S02", "Hospital")
                )
        );
    }

    @Test
    void testSearchNonexistentVertex() {
        tree.insert(createVertex("S01", "Hospital"));

        assertNull(tree.search("Universidad"));
    }

    @Test
    void testRejectInvalidSearch() {
        assertThrows(IllegalArgumentException.class,
                () -> tree.search(null));

        assertThrows(IllegalArgumentException.class,
                () -> tree.search(""));
    }

    @Test
    void testTreeHeight() {
        tree.insert(createVertex("S01", "10"));
        tree.insert(createVertex("S02", "20"));
        tree.insert(createVertex("S03", "30"));

        assertEquals(2, tree.getTreeHeight());
    }

    @Test
    void testSearchSteps() {
        tree.insert(createVertex("S01", "Hospital"));

        tree.search("Hospital");

        assertEquals(1, tree.getLastSearchSteps());
    }

    @Test
    void testEmptyTree() {
        assertTrue(tree.isEmpty());
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
        assertTrue(tree.inOrder().isEmpty());
        assertTrue(tree.isBalanced());
    }
}
