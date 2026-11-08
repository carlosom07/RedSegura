package com.redsegura.model;

import java.util.Objects;

/**
 * Representa un punto de atención o ubicación dentro de la red de emergencias
 * RedSegura.
 */
public class Vertex {

    private final String id;
    private final String name;
    private final String category;

    /**
     * Construye un vértice.
     *
     * @param id identificador único del vértice
     * @param name nombre de la ubicación
     * @param category categoría de la ubicación
     */
    public Vertex(String id, String name, String category) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(
                    "El identificador no puede estar vacío."
            );
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre no puede estar vacío."
            );
        }

        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException(
                    "La categoría no puede estar vacía."
            );
        }

        this.id = id.trim();
        this.name = name.trim();
        this.category = category.trim();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    /**
     * Dos vértices son iguales si tienen el mismo ID.
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Vertex)) {
            return false;
        }

        Vertex other = (Vertex) obj;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id + " - " + name + " (" + category + ")";
    }
}
