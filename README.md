

# RedSegura
## Árboles y grafos aplicados a una red de atención de emergencias

### 1. Descripción del proyecto

RedSegura es una aplicación desarrollada en Java que representa una red de atención de emergencias mediante estructuras de datos y algoritmos de grafos.

El sistema permite registrar puntos de atención, instituciones de salud, universidades, estaciones de transporte y otros lugares importantes de Sincelejo.

También permite consultar lugares, recorrer la red y calcular rutas de menor costo entre diferentes puntos.

### 2. Objetivo general

Desarrollar una aplicación que integre árboles binarios de búsqueda, árboles AVL y grafos para representar una red de atención de emergencias y facilitar la consulta de ubicaciones y rutas.

### 3. Tecnologías utilizadas

- Java 17.
- Apache NetBeans.
- Maven.
- JUnit 5.
- Git y GitHub.

### 4. Estructuras de datos implementadas

**Árbol Binario de Búsqueda (ABB)**

Permite almacenar y consultar los lugares registrados utilizando sus nombres como criterio de ordenamiento.

**Árbol AVL**

Es un árbol binario de búsqueda autoequilibrado. Implementa las rotaciones LL, RR, LR y RL para conservar el equilibrio.

**Grafo mixto**

Representa los lugares mediante vértices y las conexiones mediante aristas dirigidas y no dirigidas con pesos.

El grafo permite consultar tres representaciones:

- Lista de adyacencia.
- Matriz de adyacencia.
- Matriz de incidencia.

### 5. Algoritmos implementados

**BFS (Breadth-First Search)**

Realiza un recorrido por niveles utilizando una cola.

**DFS (Depth-First Search)**

Realiza un recorrido en profundidad utilizando una pila.

**Dijkstra**

Calcula la ruta de menor costo entre dos vértices utilizando una cola de prioridad.

### 6. Organización del proyecto

```text
RedSegura/
│
├── pom.xml
├── README.md
│
└── src/
    ├── main/
    │   └── java/
    │       └── com/redsegura/
    │           ├── model/
    │           ├── tree/
    │           ├── graph/
    │           ├── algorithm/
    │           ├── core/
    │           └── app/
    │
    └── test/
        └── java/
            └── com/redsegura/
                ├── tree/
                ├── graph/
                ├── algorithm/
                └── core/
```

### 7. Datos de la red

El sistema contiene:

- 12 vértices.
- 18 conexiones.
- Conexiones dirigidas y no dirigidas.
- Pesos que representan costos de recorrido en minutos para fines académicos.

### 8. Funcionalidades

La aplicación de consola permite:

1. Buscar lugares mediante ABB y AVL.
2. Demostrar las cuatro rotaciones del AVL.
3. Mostrar las representaciones del grafo.
4. Ejecutar BFS y DFS desde el vértice S02.
5. Calcular la ruta más corta entre S02 y S08.
6. Bloquear la conexión R16 y recalcular la ruta.
7. Consultar un vértice inexistente.

### 9. Ejemplo de rutas

Ruta original:

```text
S02 -> S01 -> S07 -> S08
Costo total: 24 minutos
```

Ruta alternativa al bloquear R16:

```text
S02 -> S01 -> S09 -> S10 -> S08
Costo total: 24 minutos
```

Los pesos son datos académicos de simulación y no representan tiempos oficiales de desplazamiento.

### 10. Instalación y ejecución

1. Instalar JDK 17 o una versión compatible.
2. Instalar Apache NetBeans.
3. Abrir el proyecto Maven en NetBeans.
4. Esperar a que Maven descargue las dependencias.
5. Ejecutar Clean and Build.
6. Configurar como clase principal:

```text
com.redsegura.app.RedSeguraApplication
```

7. Ejecutar el proyecto.

### 11. Pruebas unitarias

El proyecto utiliza JUnit 5 para verificar el funcionamiento de los árboles, el grafo y los algoritmos implementados.

Para ejecutar las pruebas desde NetBeans:

- Hacer clic derecho sobre el proyecto.
- Seleccionar Test.

También se pueden ejecutar desde la terminal:

```bash
mvn test
```

### 12. Autoría

Carlos Mario Osorio Marquez

Proyecto académico desarrollado para la asignatura Estructura de Datos II.

Ingeniería de Sistemas.
