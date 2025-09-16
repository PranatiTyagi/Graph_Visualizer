# Graph Service (Java 24 Backend)

A Spring Boot REST API service that provides graph algorithms (BFS, DFS) with support for directed and undirected graphs. Built with Java 24 and modern Spring Boot.

## Features

- **BFS (Breadth-First Search)**: Level-order traversal algorithm
- **DFS (Depth-First Search)**: Depth-first traversal algorithm
- **Directed & Undirected Graphs**: Support for both graph types
- **Path Finding**: Check if path exists between nodes
- **REST API**: Clean REST endpoints for frontend integration

## Technologies Used

- **Java 24**: Latest Java with modern features
- **Spring Boot 3.1.0**: Production-ready framework
- **Spring Web**: REST API capabilities
- **Maven**: Dependency management

## API Endpoints

### POST /api/graph/bfs
Run BFS traversal on a graph.

**Request Body:**
```json
{
  "nodes": [
    {"id": 1, "label": "A"},
    {"id": 2, "label": "B"}
  ],
  "edges": [
    {"source": 1, "target": 2, "type": "directed", "weight": 1.0}
  ],
  "startNodeId": 1
}
```

**Response:**
```json
{
  "algorithm": "BFS",
  "traversalOrder": [1, 2, 3],
  "startNode": 1
}
```

### POST /api/graph/dfs
Run DFS traversal on a graph.

**Request Body:** Same as BFS

**Response:**
```json
{
  "algorithm": "DFS",
  "traversalOrder": [1, 3, 2],
  "startNode": 1
}
```

### POST /api/graph/hasPath
Check if a path exists between two nodes.

**Request Body:**
```json
{
  "nodes": [...],
  "edges": [...],
  "startNodeId": 1,
  "endNodeId": 3
}
```

**Response:**
```json
{
  "hasPath": true,
  "startNode": 1,
  "endNode": 3
}
```

### POST /api/graph/shortestPath
Find the shortest path between two nodes.

**Request Body:** Same as hasPath

**Response:**
```json
{
  "path": [1, 2, 3],
  "startNode": 1,
  "endNode": 3,
  "pathLength": 2
}
```

## Running the Service

### Prerequisites
- Java 24 or higher
- Maven 3.6+

### Start the Service
```bash
cd graph-service
mvn spring-boot:run
```

The service will start on `http://localhost:8080`

### Build JAR
```bash
mvn clean package
java -jar target/graph-service-0.0.1-SNAPSHOT.jar
```

## Project Structure

```
graph-service/
├── src/main/java/com/example/graphservice/
│   ├── GraphServiceApplication.java          # Main Spring Boot application
│   ├── controller/
│   │   └── GraphController.java              # REST API endpoints
│   ├── model/
│   │   ├── Node.java                         # Node data model
│   │   └── Edge.java                         # Edge data model
│   └── service/
│       └── GraphAlgorithmService.java        # Algorithm implementations
├── src/main/resources/
│   └── application.properties                # Configuration
└── pom.xml                                   # Maven dependencies
```

## Algorithm Details

### BFS Implementation
- Uses `Queue` for level-order traversal
- Tracks visited nodes to avoid cycles
- Builds parent map for path reconstruction
- Handles both directed and undirected graphs

### DFS Implementation
- Uses `Stack` for depth-first traversal
- Recursive approach with proper backtracking
- Maintains discovery order
- Supports graph directionality

### Graph Representation
- **Adjacency List**: Efficient for sparse graphs
- **Directed Support**: Only traverses edges in correct direction
- **Undirected Support**: Bidirectional traversal
- **Weighted Edges**: Ready for future weighted algorithms

## Testing

Run the tests:
```bash
mvn test
```

## Integration with Frontend

This service is designed to work with the Graph Visualizer frontend:

1. **Frontend** creates graph interactively with D3.js
2. **Frontend** calls Java API with graph data
3. **Java service** runs algorithms and returns results
4. **Frontend** animates the algorithm results

## Future Enhancements

- Add weighted graph algorithms (Dijkstra, Bellman-Ford)
- Implement graph search algorithms (A*, Greedy)
- Make the UI more intuitive for the user
