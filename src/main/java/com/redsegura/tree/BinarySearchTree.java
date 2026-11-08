package com.redsegura.tree;

import com.redsegura.model.Vertex;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementa un arbol binario de busqueda (ABB) para almacenar vertices de la
 * red RedSegura.
 *
 * Los vertices se organizan alfabeticamente por nombre.
 */
public class BinarySearchTree {

    /**
     * Nodo interno del arbol.
     */
    private static class Node {

        private Vertex vertex;
        private Node left;
        private Node right;

        public Node(Vertex vertex) {
            this.vertex = vertex;
        }
    }

    private Node root;
    private int size;
    private int lastSearchSteps;

    /**
     * Inserta un vertice manteniendo el orden del ABB.
     */
    public void insert(Vertex vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException(
                    "El vertice no puede ser null."
            );
        }

        root = insertRecursive(root, vertex);
    }

    private Node insertRecursive(Node current, Vertex vertex) {

        if (current == null) {
            size++;
            return new Node(vertex);
        }

        int comparison = vertex.getName().compareToIgnoreCase(
                current.vertex.getName()
        );

        if (comparison < 0) {
            current.left = insertRecursive(current.left, vertex);
        } else if (comparison > 0) {
            current.right = insertRecursive(current.right, vertex);
        } else {
            throw new IllegalArgumentException(
                    "Ya existe un vertice con ese nombre."
            );
        }

        return current;
    }

    /**
     * Busca un vertice por su nombre. Retorna null si no existe.
     */
    public Vertex search(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre no puede estar vacio."
            );
        }

        lastSearchSteps = 0;
        return searchRecursive(root, name.trim());
    }

    private Vertex searchRecursive(Node current, String name) {

        if (current == null) {
            return null;
        }

        lastSearchSteps++;

        int comparison = name.compareToIgnoreCase(
                current.vertex.getName()
        );

        if (comparison == 0) {
            return current.vertex;
        }

        if (comparison < 0) {
            return searchRecursive(current.left, name);
        }

        return searchRecursive(current.right, name);
    }

    /**
     * Retorna la cantidad de nodos visitados durante la ultima busqueda.
     */
    public int getLastSearchSteps() {
        return lastSearchSteps;
    }

    /**
     * Retorna la cantidad de vertices almacenados.
     */
    public int size() {
        return size;
    }

    /**
     * Indica si el arbol esta vacio.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Retorna los vertices ordenados alfabeticamente.
     */
    public List<Vertex> inOrder() {
        List<Vertex> result = new ArrayList<>();
        inOrderRecursive(root, result);
        return result;
    }

    private void inOrderRecursive(
            Node current, List<Vertex> result
    ) {
        if (current != null) {
            inOrderRecursive(current.left, result);
            result.add(current.vertex);
            inOrderRecursive(current.right, result);
        }
    }
}
