package com.redsegura.tree;

import com.redsegura.model.Vertex;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementacion manual de un arbol AVL. Los vertices se organizan
 * alfabeticamente por nombre.
 */
public class AvlTree {

    private static class Node {

        Vertex vertex;
        Node left;
        Node right;
        int height;

        Node(Vertex vertex) {
            this.vertex = vertex;
            this.height = 1;
        }
    }

    private Node root;
    private int size;
    private int lastSearchSteps;

    /**
     * Inserta un vertice y mantiene el equilibrio AVL.
     */
    public void insert(Vertex vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException(
                    "El vertice no puede ser null."
            );
        }

        if (searchNameExists(root, vertex.getName())) {
            throw new IllegalArgumentException(
                    "Ya existe un vertice con ese nombre."
            );
        }

        root = insertRecursive(root, vertex);
        size++;
    }

    private Node insertRecursive(Node node, Vertex vertex) {

        if (node == null) {
            return new Node(vertex);
        }

        int comparison = vertex.getName()
                .compareToIgnoreCase(node.vertex.getName());

        if (comparison < 0) {
            node.left = insertRecursive(node.left, vertex);
        } else {
            node.right = insertRecursive(node.right, vertex);
        }

        updateHeight(node);

        int balance = getBalance(node);

        // Caso LL: rotacion simple a la derecha.
        if (balance > 1
                && vertex.getName().compareToIgnoreCase(
                        node.left.vertex.getName()) < 0) {
            return rightRotate(node);
        }

        // Caso RR: rotacion simple a la izquierda.
        if (balance < -1
                && vertex.getName().compareToIgnoreCase(
                        node.right.vertex.getName()) > 0) {
            return leftRotate(node);
        }

        // Caso LR: rotacion izquierda y luego derecha.
        if (balance > 1
                && vertex.getName().compareToIgnoreCase(
                        node.left.vertex.getName()) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Caso RL: rotacion derecha y luego izquierda.
        if (balance < -1
                && vertex.getName().compareToIgnoreCase(
                        node.right.vertex.getName()) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    /**
     * Rotacion simple hacia la derecha.
     */
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node temporary = x.right;

        x.right = y;
        y.left = temporary;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    /**
     * Rotacion simple hacia la izquierda.
     */
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node temporary = y.left;

        y.left = x;
        x.right = temporary;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    private int getHeight(Node node) {
        return node == null ? 0 : node.height;
    }

    private void updateHeight(Node node) {
        node.height = 1 + Math.max(
                getHeight(node.left),
                getHeight(node.right)
        );
    }

    /**
     * Factor de equilibrio: altura izquierda - altura derecha.
     */
    private int getBalance(Node node) {
        if (node == null) {
            return 0;
        }

        return getHeight(node.left) - getHeight(node.right);
    }

    /**
     * Busca un vertice por su nombre.
     */
    public Vertex search(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre de busqueda no puede estar vacio."
            );
        }

        lastSearchSteps = 0;
        Node current = root;

        while (current != null) {
            lastSearchSteps++;

            int comparison = name.trim()
                    .compareToIgnoreCase(current.vertex.getName());

            if (comparison == 0) {
                return current.vertex;
            } else if (comparison < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return null;
    }

    private boolean searchNameExists(Node node, String name) {
        if (node == null) {
            return false;
        }

        int comparison = name.compareToIgnoreCase(
                node.vertex.getName()
        );

        if (comparison == 0) {
            return true;
        }

        if (comparison < 0) {
            return searchNameExists(node.left, name);
        }

        return searchNameExists(node.right, name);
    }

    /**
     * Devuelve los vertices ordenados alfabeticamente.
     */
    public List<Vertex> inOrder() {
        List<Vertex> result = new ArrayList<>();
        inOrderRecursive(root, result);
        return result;
    }

    private void inOrderRecursive(
            Node node, List<Vertex> result
    ) {
        if (node != null) {
            inOrderRecursive(node.left, result);
            result.add(node.vertex);
            inOrderRecursive(node.right, result);
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getLastSearchSteps() {
        return lastSearchSteps;
    }

    /**
     * Permite consultar la raiz para demostrar las rotaciones.
     */
    public Vertex getRoot() {
        return root == null ? null : root.vertex;
    }

    public int getTreeHeight() {
        return getHeight(root);
    }

    /**
     * Comprueba que todos los nodos mantienen un factor de equilibrio entre -1
     * y 1.
     */
    public boolean isBalanced() {
        return isBalancedRecursive(root);
    }

    private boolean isBalancedRecursive(Node node) {
        if (node == null) {
            return true;
        }

        int balance = getBalance(node);

        return Math.abs(balance) <= 1
                && isBalancedRecursive(node.left)
                && isBalancedRecursive(node.right);
    }
}
