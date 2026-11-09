package com.redsegura.core;

import com.redsegura.model.Vertex;
import com.redsegura.model.Edge;
import com.redsegura.tree.BinarySearchTree;
import com.redsegura.tree.AvlTree;
import com.redsegura.graph.MixedGraph;
import com.redsegura.algorithm.GraphTraversal;
import com.redsegura.algorithm.DijkstraAlgorithm;

/**
 * Nucleo central del sistema RedSegura. Integra los arboles, el grafo y los
 * algoritmos.
 */
public class RedSeguraCore {

    private final BinarySearchTree binarySearchTree;
    private final AvlTree avlTree;
    private final MixedGraph graph;
    private final GraphTraversal traversal;
    private final DijkstraAlgorithm dijkstra;

    public RedSeguraCore() {

        binarySearchTree = new BinarySearchTree();
        avlTree = new AvlTree();
        graph = new MixedGraph();

        loadVertices();
        loadEdges();

        traversal = new GraphTraversal(graph);
        dijkstra = new DijkstraAlgorithm(graph);
    }

    /**
     * Carga los 12 vertices de la red.
     */
    private void loadVertices() {

        addVertex("S01", "UAJS Sede C", "Universidad");
        addVertex("S02", "UAJS Sede E", "Universidad");
        addVertex("S03", "Universidad de Sucre", "Universidad");
        addVertex("S04", "CECAR", "Universidad");
        addVertex("S05", "Hospital Universitario", "Salud");
        addVertex("S06", "Clinica Las Americas", "Salud");
        addVertex("S07", "Estacion Central SETP", "Transporte");
        addVertex("S08", "Terminal de Transporte", "Transporte");
        addVertex("S09", "Parque Santander", "Punto de encuentro");
        addVertex("S10", "Mercado de Sincelejo", "Comercio");
        addVertex("S11", "PEP Argelia", "Paradero");
        addVertex("S12", "Glorieta Sincelejito", "Movilidad");
    }

    /**
     * Registra un vertice en las tres estructuras.
     */
    private void addVertex(
            String id, String name, String category
    ) {

        Vertex vertex = new Vertex(id, name, category);

        graph.addVertex(vertex);
        binarySearchTree.insert(vertex);
        avlTree.insert(vertex);
    }

    /**
     * Carga las 18 conexiones de la red.
     */
    private void loadEdges() {

        // R01
        addEdge("R01", "S01", "S07", 4, true,
                "Corredor universitario");

        // R02
        addEdge("R02", "S07", "S01", 6, true,
                "Retorno por via alterna");

        // R03
        addEdge("R03", "S01", "S09", 5, false,
                "Corredor peatonal");

        // R04
        addEdge("R04", "S02", "S01", 8, false,
                "Conexion entre sedes");

        // R05
        addEdge("R05", "S02", "S06", 10, true,
                "Ruta de atencion");

        // R06
        addEdge("R06", "S06", "S05", 4, true,
                "Corredor clinico");

        // R07
        addEdge("R07", "S05", "S09", 6, false,
                "Via hospitalaria");

        // R08
        addEdge("R08", "S09", "S10", 3, false,
                "Centro historico");

        // R09
        addEdge("R09", "S10", "S08", 8, true,
                "Salida al terminal");

        // R10
        addEdge("R10", "S08", "S10", 9, true,
                "Ingreso desde terminal");

        // R11
        addEdge("R11", "S03", "S12", 4, false,
                "Corredor Puerta Roja");

        // R12
        addEdge("R12", "S12", "S09", 7, false,
                "Troncal al centro");

        // R13
        addEdge("R13", "S04", "S11", 6, false,
                "Conexion universitaria");

        // R14
        addEdge("R14", "S11", "S07", 8, true,
                "Ruta SETP al centro");

        // R15
        addEdge("R15", "S07", "S09", 3, false,
                "Transferencia central");

        // R16
        addEdge("R16", "S07", "S08", 12, true,
                "Ruta SETP al terminal");

        // R17
        addEdge("R17", "S09", "S03", 7, true,
                "Ruta hacia universidad");

        // R18
        addEdge("R18", "S12", "S04", 9, true,
                "Ruta hacia CECAR");
    }

    /**
     * Crea una conexion utilizando los vertices registrados.
     */
    private void addEdge(
            String id,
            String sourceId,
            String destinationId,
            int weight,
            boolean directed,
            String description
    ) {

        Vertex source = graph.getVertex(sourceId);
        Vertex destination = graph.getVertex(destinationId);

        Edge edge = new Edge(
                id,
                source,
                destination,
                weight,
                directed,
                description
        );

        graph.addEdge(edge);
    }

    // Acceso a las estructuras principales.
    public BinarySearchTree getBinarySearchTree() {
        return binarySearchTree;
    }

    public AvlTree getAvlTree() {
        return avlTree;
    }

    public MixedGraph getGraph() {
        return graph;
    }

    public GraphTraversal getTraversal() {
        return traversal;
    }

    public DijkstraAlgorithm getDijkstra() {
        return dijkstra;
    }

    // Consultas de informacion.
    public Vertex searchBinarySearchTree(String name) {
        return binarySearchTree.search(name);
    }

    public Vertex searchAvlTree(String name) {
        return avlTree.search(name);
    }

    public int getVertexCount() {
        return graph.getVertexCount();
    }

    public int getEdgeCount() {
        return graph.getEdgeCount();
    }

    // Bloqueo y habilitacion de conexiones.
    public void blockEdge(String edgeId) {
        graph.blockEdge(edgeId);
    }

    public void unblockEdge(String edgeId) {
        graph.unblockEdge(edgeId);
    }
}
