package com.redsegura.app;

import com.redsegura.core.RedSeguraCore;
import com.redsegura.model.Vertex;
import com.redsegura.tree.AvlTree;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RedSeguraApplication {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final RedSeguraCore CORE = new RedSeguraCore();

    public static void main(String[] args) {

        int opcion;

        System.out.println("======================================");
        System.out.println("          SISTEMA REDSEGURA");
        System.out.println("   Red de atencion de emergencias");
        System.out.println("======================================");

        System.out.println("Vertices cargados: " + CORE.getVertexCount());
        System.out.println("Conexiones cargadas: " + CORE.getEdgeCount());

        do {
            mostrarMenu();
            opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1:
                        buscarLugar();
                        break;
                    case 2:
                        demostrarRotaciones();
                        break;
                    case 3:
                        mostrarRepresentaciones();
                        break;
                    case 4:
                        ejecutarRecorridos();
                        break;
                    case 5:
                        calcularRutaOriginal();
                        break;
                    case 6:
                        calcularRutaAlternativa();
                        break;
                    case 7:
                        consultarVerticeInexistente();
                        break;
                    case 0:
                        System.out.println("\nCerrando RedSegura...");
                        break;
                    default:
                        System.out.println("\nOpcion no valida.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("\nNo se pudo completar la operacion: "
                        + e.getMessage());
            }

            if (opcion != 0) {
                System.out.println("\nPresiona ENTER para continuar...");
                SCANNER.nextLine();
            }

        } while (opcion != 0);

        SCANNER.close();
    }

    private static void mostrarMenu() {

        System.out.println("\n============== MENU PRINCIPAL ==============");
        System.out.println("1. Buscar un lugar en ABB y AVL");
        System.out.println("2. Demostrar rotaciones del AVL");
        System.out.println("3. Mostrar representaciones del grafo");
        System.out.println("4. Ejecutar BFS y DFS desde S02");
        System.out.println("5. Calcular ruta mas corta S02 - S08");
        System.out.println("6. Bloquear R16 y recalcular ruta");
        System.out.println("7. Consultar un vertice inexistente");
        System.out.println("0. Salir");
        System.out.println("=============================================");
        System.out.print("Selecciona una opcion: ");
    }

    private static int leerOpcion() {

        String entrada = SCANNER.nextLine();

        try {
            return Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // CONSULTA 1: busqueda en ABB y AVL.
    private static void buscarLugar() {

        String nombre = "Hospital Universitario";

        System.out.println("\n--- BUSQUEDA EN ARBOLES ---");
        System.out.println("Lugar: " + nombre);

        Vertex resultadoABB
                = CORE.searchBinarySearchTree(nombre);

        int pasosABB
                = CORE.getBinarySearchTree().getLastSearchSteps();

        Vertex resultadoAVL
                = CORE.searchAvlTree(nombre);

        int pasosAVL
                = CORE.getAvlTree().getLastSearchSteps();

        System.out.println("\nArbol Binario de Busqueda:");

        System.out.println("Resultado: " + resultadoABB);
        System.out.println("Pasos: " + pasosABB);

        System.out.println("\nArbol AVL:");

        System.out.println("Resultado: " + resultadoAVL);
        System.out.println("Pasos: " + pasosAVL);
    }

    // CONSULTA 2: demostracion de las cuatro rotaciones AVL.
    private static void demostrarRotaciones() {

        System.out.println("\n--- ROTACIONES DEL ARBOL AVL ---");

        demostrarCaso("LL", new String[]{"30", "20", "10"});
        demostrarCaso("RR", new String[]{"10", "20", "30"});
        demostrarCaso("LR", new String[]{"30", "10", "20"});
        demostrarCaso("RL", new String[]{"10", "30", "20"});
    }

    private static void demostrarCaso(
            String tipo, String[] valores) {

        AvlTree arbol = new AvlTree();

        System.out.println("\nRotacion " + tipo);
        System.out.println("Orden de insercion:");

        for (int i = 0; i < valores.length; i++) {

            System.out.print(valores[i] + " ");

            arbol.insert(new Vertex(
                    "T" + i,
                    valores[i],
                    "Prueba"
            ));
        }

        System.out.println("\nRecorrido inorden:");

        for (Vertex vertex : arbol.inOrder()) {
            System.out.print(vertex.getName() + " ");
        }

        System.out.println("\nAltura del arbol: "
                + arbol.getTreeHeight());

        System.out.println("Balance correcto: "
                + arbol.isBalanced());
    }

    // CONSULTA 3: representaciones del grafo.
    private static void mostrarRepresentaciones() {

        System.out.println("\n--- REPRESENTACIONES DEL GRAFO ---");

        System.out.println("\nLISTA DE ADYACENCIA:");

        for (Map.Entry<String, List<String>> entry
                : CORE.getGraph().adjacencyList().entrySet()) {

            System.out.println(entry.getKey() + " -> "
                    + entry.getValue());
        }

        System.out.println("\nMATRIZ DE ADYACENCIA:");

        int[][] matrizAdyacencia
                = CORE.getGraph().adjacencyMatrix();

        imprimirMatriz(
                matrizAdyacencia,
                CORE.getGraph().getVertexIds(),
                CORE.getGraph().getVertexIds()
        );

        System.out.println("\nMATRIZ DE INCIDENCIA:");

        int[][] matrizIncidencia
                = CORE.getGraph().incidenceMatrix();

        imprimirMatriz(
                matrizIncidencia,
                CORE.getGraph().getVertexIds(),
                CORE.getGraph().getEdgeIds()
        );
    }

    private static void imprimirMatriz(
            int[][] matriz,
            List<String> filas,
            List<String> columnas) {

        System.out.printf("%-8s", "");

        for (String columna : columnas) {
            System.out.printf("%-8s", columna);
        }

        System.out.println();

        for (int i = 0; i < matriz.length; i++) {

            System.out.printf("%-8s", filas.get(i));

            for (int j = 0; j < matriz[i].length; j++) {
                System.out.printf("%-8d", matriz[i][j]);
            }

            System.out.println();
        }
    }

    // CONSULTA 4: BFS y DFS.
    private static void ejecutarRecorridos() {

        System.out.println("\n--- RECORRIDOS DEL GRAFO ---");

        System.out.println("\nBFS desde S02:");

        List<Vertex> recorridoBFS
                = CORE.getTraversal().bfs("S02");

        imprimirRecorrido(recorridoBFS);

        System.out.println("\nDFS desde S02:");

        List<Vertex> recorridoDFS
                = CORE.getTraversal().dfs("S02");

        imprimirRecorrido(recorridoDFS);
    }

    private static void imprimirRecorrido(
            List<Vertex> recorrido) {

        for (Vertex vertex : recorrido) {
            System.out.print(vertex.getId() + " ");
        }

        System.out.println();
        System.out.println("Vertices visitados: "
                + recorrido.size());
    }

    // CONSULTA 5: ruta original.
    private static void calcularRutaOriginal() {

        System.out.println("\n--- RUTA ORIGINAL ---");

        var resultado = CORE.getDijkstra()
                .findShortestPath("S02", "S08");

        System.out.println(resultado);
        System.out.println("Distancia total: "
                + resultado.getDistance() + " minutos");
    }

    // CONSULTA 6: bloqueo de R16.
    private static void calcularRutaAlternativa() {

        System.out.println("\n--- RUTA ALTERNATIVA ---");

        CORE.blockEdge("R16");

        System.out.println("Conexion R16 bloqueada.");

        var resultado = CORE.getDijkstra()
                .findShortestPath("S02", "S08");

        System.out.println(resultado);
        System.out.println("Distancia total: "
                + resultado.getDistance() + " minutos");

        System.out.println("\nLa conexion R16 permanece bloqueada "
                + "durante esta ejecucion.");
    }

    // CONSULTA 7: consulta de un vertice inexistente.
    private static void consultarVerticeInexistente() {

        System.out.println("\n--- CONSULTA DE VERTICE INEXISTENTE ---");

        String id = "S99";

        Vertex resultado = CORE.getGraph().getVertex(id);

        if (resultado == null) {
            System.out.println("El vertice " + id
                    + " no existe en la red.");
            System.out.println("El sistema continua funcionando.");
        } else {
            System.out.println(resultado);
        }
    }
}
