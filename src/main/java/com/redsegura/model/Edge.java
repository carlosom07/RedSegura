package com.redsegura.model;

import java.util.Objects;

/**
 * Representa una conexión entre dos vértices de la red de emergencias
 * RedSegura.
 *
 * Una conexión puede ser dirigida o no dirigida.
 */
public class Edge {

    private final String id;
    private final Vertex source;
    private final Vertex destination;
    private final int weight;
    private final boolean directed;
    private final String description;

    /**
     * Constructor de una conexión.
     *
     * @param id identificador de la conexión
     * @param source vértice de origen
     * @param destination vértice de destino
     * @param weight peso de la conexión en minutos
     * @param directed indica si la conexión es dirigida
     * @param description descripción de la conexión
     */
    public Edge(String id, Vertex source, Vertex destination,
            int weight, boolean directed, String description) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(
                    "El identificador de la conexión no puede estar vacío."
            );
        }

        if (source == null || destination == null) {
            throw new IllegalArgumentException(
                    "Los vértices de origen y destino son obligatorios."
            );
        }

        if (weight <= 0) {
            throw new IllegalArgumentException(
                    "El peso de la conexión debe ser mayor que cero."
            );
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException(
                    "La descripción no puede estar vacía."
            );
        }

        this.id = id.trim();
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.directed = directed;
        this.description = description.trim();
    }

    public String getId() {
        return id;
    }

    public Vertex getSource() {
        return source;
    }

    public Vertex getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isDirected() {
        return directed;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Compara conexiones utilizando su identificador.
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Edge)) {
            return false;
        }

        Edge other = (Edge) obj;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Devuelve una representación legible de la conexión.
     */
    @Override
    public String toString() {

        String arrow = directed ? " -> " : " <-> ";

        return id + ": "
                + source.getId()
                + arrow
                + destination.getId()
                + " | Peso: " + weight
                + " minutos | "
                + description;
    }
}
