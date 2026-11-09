# INFORME TÉCNICO
## Proyecto RedSegura

### 1. Introducción

RedSegura es un sistema académico desarrollado en Java que representa una red de atención de emergencias mediante estructuras de datos no lineales y algoritmos de grafos.

El proyecto permite almacenar lugares de interés, consultar información, recorrer conexiones y calcular rutas de menor costo entre diferentes puntos de la red.

La aplicación representa una red mixta, compuesta por conexiones dirigidas y no dirigidas, que permite simular situaciones en las que algunas rutas pueden encontrarse bloqueadas.

### 2. Objetivo

Implementar estructuras de datos y algoritmos que permitan representar, consultar y analizar una red de atención de emergencias, aplicando los conocimientos adquiridos en la asignatura Estructura de Datos II.

### 3. Tecnologías utilizadas

- Java 17.
- Apache NetBeans IDE.
- Maven.
- JUnit 5.
- Git.
- GitHub.

### 4. Modelo de datos

El sistema representa cada lugar mediante un vértice que contiene los siguientes atributos:

- Identificador.
- Nombre.
- Categoría.

Las conexiones entre los lugares se representan mediante aristas que contienen:

- Identificador.
- Vértice de origen.
- Vértice de destino.
- Peso.
- Orientación.
- Descripción.

La red está compuesta por 12 vértices y 18 conexiones dirigidas y no dirigidas.

Los pesos representan costos académicos de recorrido expresados en minutos. Estos valores son simulados y se utilizan para demostrar el funcionamiento de los algoritmos, no representan tiempos oficiales de desplazamiento.

### 5. Estructuras de datos implementadas

#### 5.1 Árbol Binario de Búsqueda (ABB)

Se implementó manualmente un Árbol Binario de Búsqueda para organizar los lugares por nombre.

Permite insertar elementos, buscar lugares y recorrer el árbol en orden.

Las operaciones de búsqueda e inserción dependen de la altura del árbol.

**Complejidad:**

- Búsqueda: O(log n) en promedio y O(n) en el peor caso.
- Inserción: O(log n) en promedio y O(n) en el peor caso.
- Recorrido inorden: O(n).

#### 5.2 Árbol AVL

Se implementó manualmente un árbol AVL para mantener el equilibrio después de las inserciones.

El árbol utiliza la altura de sus nodos y el factor de equilibrio para determinar cuándo es necesario realizar rotaciones.

Se desarrollaron los cuatro tipos de rotaciones:

- **LL:** rotación simple a la derecha.
- **RR:** rotación simple a la izquierda.
- **LR:** rotación doble izquierda-derecha.
- **RL:** rotación doble derecha-izquierda.

**Complejidad:**

- Búsqueda: O(log n).
- Inserción: O(log n).
- Recorrido inorden: O(n).

#### 5.3 Grafo mixto

Se implementó un grafo mixto que permite almacenar conexiones dirigidas y no dirigidas entre diferentes vértices.

Se utilizaron estructuras de almacenamiento para administrar los vértices y las aristas.

También se desarrollaron tres representaciones del grafo:

- **Lista de adyacencia:** permite consultar las conexiones de cada vértice.
- **Matriz de adyacencia:** representa las conexiones entre pares de vértices mediante una matriz.
- **Matriz de incidencia:** representa la relación entre los vértices y las aristas.

El sistema permite bloquear y habilitar conexiones para simular cambios en la disponibilidad de las rutas.

### 6. Algoritmos implementados

#### 6.1 BFS (Breadth-First Search)

El recorrido en anchura (BFS) utiliza una cola FIFO para visitar primero los vértices más cercanos al punto de partida.

Antes de iniciar el recorrido, se construye una lista de adyacencia temporal que permite consultar directamente los vecinos de cada vértice.

El algoritmo utiliza una estructura de visitados para evitar procesar varias veces el mismo vértice.

**Complejidad temporal:** O(V + E).

#### 6.2 DFS (Depth-First Search)

El recorrido en profundidad (DFS) utiliza una pila LIFO para explorar una rama del grafo antes de continuar con las demás.

Al igual que BFS, utiliza una lista de adyacencia temporal para consultar las conexiones disponibles sin recorrer todas las aristas repetidamente.

También utiliza una estructura de visitados para evitar ciclos y repeticiones durante el recorrido.

**Complejidad temporal:** O(V + E).

#### 6.3 Dijkstra

Se implementó el algoritmo de Dijkstra para calcular la ruta de menor costo entre dos vértices.

El algoritmo utiliza:

- Una lista de adyacencia temporal para consultar los vecinos.
- Una cola de prioridad (`PriorityQueue`) para seleccionar el vértice con menor distancia acumulada.
- Un mapa de distancias para almacenar los costos mínimos conocidos.
- Un mapa de vértices anteriores para reconstruir la ruta final.

También respeta la dirección de las conexiones y excluye las aristas bloqueadas.

**Complejidad temporal:** O((V + E) log V), utilizando listas de adyacencia y una cola de prioridad con un grafo simple.

#### 6.4 Resumen de complejidad

| Algoritmo | Complejidad temporal |
|---|---|
| Búsqueda en ABB | O(log n) promedio |
| Búsqueda en AVL | O(log n) |
| BFS | O(V + E) |
| DFS | O(V + E) |
| Dijkstra | O((V + E) log V) |

Donde V representa el número de vértices y E el número de aristas del grafo.

### 7. Decisiones de diseño

Durante el desarrollo del proyecto se tomaron las siguientes decisiones:

- Se organizaron las clases en paquetes para facilitar el mantenimiento y la comprensión del código.
- Se implementaron manualmente los árboles binarios de búsqueda y AVL.
- Se desarrollaron manualmente los recorridos BFS y DFS.
- Se utilizó `PriorityQueue` para optimizar la selección de vértices en Dijkstra.
- Se construyeron listas de adyacencia temporales para optimizar los recorridos y la búsqueda de rutas.
- Se separó la lógica central de la interfaz de consola mediante las clases correspondientes.
- Se incorporó el bloqueo y habilitación de conexiones para simular cambios en la red.
- Se utilizaron pruebas unitarias con JUnit 5 para verificar el funcionamiento de las estructuras y algoritmos.

### 8. Resultados de las consultas

El sistema permite ejecutar diferentes consultas mediante un menú interactivo en consola.

#### Consulta 1. Búsqueda de Hospital Universitario

Se realiza la búsqueda del lugar Hospital Universitario utilizando el Árbol Binario de Búsqueda y el árbol AVL.

El sistema muestra los resultados y compara los pasos utilizados por cada estructura.

#### Consulta 2. Rotaciones AVL

Se verifican los cuatro tipos de rotaciones del árbol AVL:

- LL.
- RR.
- LR.
- RL.

Se comprueba que el árbol conserve sus propiedades de equilibrio después de las inserciones.

#### Consulta 3. Representaciones del grafo

Se muestran las tres representaciones implementadas:

- Lista de adyacencia.
- Matriz de adyacencia.
- Matriz de incidencia.

Estas representaciones permiten observar las conexiones dirigidas y no dirigidas de la red.

#### Consulta 4. Recorridos BFS y DFS

Se ejecutan los recorridos en anchura y profundidad tomando como punto de partida el vértice S02.

El sistema muestra el orden de visita de los lugares alcanzables desde ese punto.

#### Consulta 5. Ruta original mediante Dijkstra

Se calcula la ruta de menor costo desde S02 hasta S08.

**Ruta obtenida:**

S02 → S01 → S07 → S08

**Costo total: 24 minutos.**

#### Consulta 6. Bloqueo de la conexión R16

Se bloquea la conexión R16, que corresponde a la ruta directa entre S07 y S08.

Posteriormente, se ejecuta nuevamente Dijkstra para encontrar una ruta alternativa.

**Ruta obtenida:**

S02 → S01 → S09 → S10 → S08

**Costo total: 24 minutos.**

Este resultado demuestra que el algoritmo puede encontrar una ruta alternativa cuando una conexión deja de estar disponible.

#### Consulta 7. Consulta de un vértice inexistente

Se realiza una búsqueda utilizando el identificador S99.

El sistema informa que el vértice no existe y continúa funcionando sin finalizar la aplicación.

### 9. Pruebas unitarias

Se desarrollaron pruebas unitarias utilizando JUnit 5 para verificar el funcionamiento de los diferentes componentes del proyecto.

Las pruebas contemplan los siguientes aspectos:

**Árbol Binario de Búsqueda:**
- Inserción de elementos.
- Búsqueda de elementos existentes.
- Búsqueda de elementos inexistentes.
- Árbol vacío.
- Elementos duplicados.
- Recorrido inorden.

**Árbol AVL:**
- Inserciones y búsquedas.
- Verificación de equilibrio.
- Rotaciones LL, RR, LR y RL.
- Elementos duplicados.
- Árbol vacío.
- Altura del árbol.

**Grafo mixto:**
- Inserción de vértices y aristas.
- Conexiones dirigidas y no dirigidas.
- Matriz de adyacencia.
- Matriz de incidencia.
- Bloqueo y habilitación de conexiones.
- Validación de vértices inexistentes.

**Recorridos BFS y DFS:**
- Recorrido desde diferentes vértices.
- Orden de visita.
- Vértices no alcanzables.
- Identificadores inválidos.
- Grafo nulo.

**Algoritmo de Dijkstra:**
- Cálculo de la ruta original.
- Cálculo de rutas alternativas.
- Bloqueo de conexiones.
- Destinos inalcanzables.
- Origen y destino iguales.
- Identificadores inválidos.

También se desarrollaron pruebas de integración para verificar la comunicación entre los componentes principales de RedSegura.

Las pruebas se ejecutan mediante Maven y JUnit 5. El resultado definitivo se registra de acuerdo con la salida obtenida durante la ejecución de todas las pruebas del proyecto.

### 10. Conclusiones

El desarrollo de RedSegura permitió aplicar estructuras de datos no lineales y algoritmos de grafos a un problema de representación de una red de atención de emergencias.

La implementación del Árbol Binario de Búsqueda y del árbol AVL permitió comprender las diferencias entre una estructura de búsqueda convencional y una estructura equilibrada.

Por otra parte, el desarrollo del grafo mixto permitió representar diferentes tipos de conexiones y conocer las ventajas de las listas de adyacencia, las matrices de adyacencia y las matrices de incidencia.

La implementación de BFS, DFS y Dijkstra permitió realizar recorridos y calcular rutas de menor costo, considerando las conexiones disponibles y las que se encuentran bloqueadas.

Finalmente, el uso de pruebas unitarias, Maven, Git y GitHub facilitó la verificación, organización y control de versiones del proyecto.

En conclusión, RedSegura permitió integrar los conocimientos de estructuras de datos y algoritmos en una aplicación funcional, fortaleciendo la comprensión de su aplicación en situaciones prácticas.
