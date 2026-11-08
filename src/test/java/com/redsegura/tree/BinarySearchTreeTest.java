package com.redsegura.tree;

import com.redsegura.model.Vertex;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeTest {

    private BinarySearchTree tree;

    @BeforeEach
    void setUp() {
        tree = new BinarySearchTree();
    }

    // 1. Comprobar inserción y búsqueda de un vértice.
    @Test
    void testInsertAndSearch() {
        Vertex vertex = new Vertex(
                "S05", "Hospital Universitario", "Salud"
        );

        tree.insert(vertex);

        Vertex result = tree.search("Hospital Universitario");

        assertNotNull(result);
        assertEquals("S05", result.getId());
        assertEquals(1, tree.size());
    }

    // 2. Buscar un vértice que no existe.
    @Test
    void testSearchNonexistentVertex() {
        tree.insert(new Vertex(
                "S05", "Hospital Universitario", "Salud"
        ));

        Vertex result = tree.search("Clínica inexistente");

        assertNull(result);
    }

    // 3. Evitar nombres duplicados.
    @Test
    void testRejectDuplicateNames() {
        tree.insert(new Vertex(
                "S05", "Hospital Universitario", "Salud"
        ));

        Vertex duplicate = new Vertex(
                "S15", "Hospital Universitario", "Salud"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> tree.insert(duplicate)
        );
    }

    // 4. Comprobar el recorrido en orden alfabético.
    @Test
    void testInOrderTraversal() {
        tree.insert(new Vertex(
                "S05", "Hospital Universitario", "Salud"
        ));

        tree.insert(new Vertex(
                "S01", "UAJS Sede C", "Universidad"
        ));

        tree.insert(new Vertex(
                "S06", "Clinica Las Americas", "Salud"
        ));

        List<Vertex> result = tree.inOrder();

        assertEquals(3, result.size());
        assertEquals("Clinica Las Americas", result.get(0).getName());
        assertEquals("Hospital Universitario", result.get(1).getName());
        assertEquals("UAJS Sede C", result.get(2).getName());
    }

    // 5. Rechazar la inserción de un vértice nulo.
    @Test
    void testRejectNullVertex() {
        assertThrows(
                IllegalArgumentException.class,
                () -> tree.insert(null)
        );
    }

    // 6. Rechazar búsquedas con valores inválidos.
    @Test
    void testRejectInvalidSearch() {
        assertThrows(
                IllegalArgumentException.class,
                () -> tree.search(null)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> tree.search("   ")
        );
    }

    // 7. Comprobar el comportamiento de un árbol vacío.
    @Test
    void testEmptyTree() {
        assertTrue(tree.isEmpty());
        assertEquals(0, tree.size());
        assertTrue(tree.inOrder().isEmpty());
        assertNull(tree.search("Hospital Universitario"));
    }

    // 8. Comprobar el contador de pasos de búsqueda.
    @Test
    void testSearchSteps() {
        tree.insert(new Vertex(
                "S05", "Hospital Universitario", "Salud"
        ));

        tree.search("Hospital Universitario");

        assertEquals(1, tree.getLastSearchSteps());
    }
}
