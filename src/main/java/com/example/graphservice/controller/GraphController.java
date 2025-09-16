package com.example.graphservice.controller;

import com.example.graphservice.model.Edge;
import com.example.graphservice.model.Node;
import com.example.graphservice.service.GraphAlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/graph")
@CrossOrigin(origins = "*") // Allow frontend to connect
public class GraphController {

    @Autowired
    private GraphAlgorithmService graphAlgorithmService;

    @PostMapping("/bfs")
    public ResponseEntity<Map<String, Object>> runBFS(
            @RequestBody GraphRequest request) {

        List<Integer> traversalOrder = graphAlgorithmService.bfs(
            request.getNodes(),
            request.getEdges(),
            request.getStartNodeId()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("algorithm", "BFS");
        response.put("traversalOrder", traversalOrder);
        response.put("startNode", request.getStartNodeId());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/dfs")
    public ResponseEntity<Map<String, Object>> runDFS(
            @RequestBody GraphRequest request) {

        List<Integer> traversalOrder = graphAlgorithmService.dfs(
            request.getNodes(),
            request.getEdges(),
            request.getStartNodeId()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("algorithm", "DFS");
        response.put("traversalOrder", traversalOrder);
        response.put("startNode", request.getStartNodeId());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/hasPath")
    public ResponseEntity<Map<String, Object>> hasPath(
            @RequestBody PathRequest request) {

        boolean hasPath = graphAlgorithmService.hasPath(
            request.getNodes(),
            request.getEdges(),
            request.getStartNodeId(),
            request.getEndNodeId()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("hasPath", hasPath);
        response.put("startNode", request.getStartNodeId());
        response.put("endNode", request.getEndNodeId());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/shortestPath")
    public ResponseEntity<Map<String, Object>> shortestPath(
            @RequestBody PathRequest request) {

        List<Integer> path = graphAlgorithmService.shortestPath(
            request.getNodes(),
            request.getEdges(),
            request.getStartNodeId(),
            request.getEndNodeId()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("path", path);
        response.put("startNode", request.getStartNodeId());
        response.put("endNode", request.getEndNodeId());
        response.put("pathLength", path.size() > 0 ? path.size() - 1 : 0);

        return ResponseEntity.ok(response);
    }

    // Request DTOs
    public static class GraphRequest {
        private List<Node> nodes;
        private List<Edge> edges;
        private int startNodeId;

        // Getters and setters
        public List<Node> getNodes() { return nodes; }
        public void setNodes(List<Node> nodes) { this.nodes = nodes; }

        public List<Edge> getEdges() { return edges; }
        public void setEdges(List<Edge> edges) { this.edges = edges; }

        public int getStartNodeId() { return startNodeId; }
        public void setStartNodeId(int startNodeId) { this.startNodeId = startNodeId; }
    }

    public static class PathRequest extends GraphRequest {
        private int endNodeId;

        public int getEndNodeId() { return endNodeId; }
        public void setEndNodeId(int endNodeId) { this.endNodeId = endNodeId; }
    }
}
