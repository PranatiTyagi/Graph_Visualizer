package com.example.graphservice.service;

import com.example.graphservice.model.Edge;
import com.example.graphservice.model.Node;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GraphAlgorithmService {

    public List<Integer> bfs(List<Node> nodes, List<Edge> edges, int startNodeId) {
        List<Integer> traversalOrder = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        Map<Integer, Integer> parent = new HashMap<>();

        // Create adjacency list
        Map<Integer, List<Integer>> adjacencyList = buildAdjacencyList(nodes, edges);

        queue.add(startNodeId);
        visited.add(startNodeId);

        while (!queue.isEmpty()) {
            int currentNodeId = queue.poll();
            traversalOrder.add(currentNodeId);

            // Get neighbors based on edge directionality
            List<Integer> neighbors = adjacencyList.getOrDefault(currentNodeId, new ArrayList<>());
            for (int neighborId : neighbors) {
                if (!visited.contains(neighborId)) {
                    visited.add(neighborId);
                    queue.add(neighborId);
                    parent.put(neighborId, currentNodeId);
                }
            }
        }

        return traversalOrder;
    }

    public List<Integer> dfs(List<Node> nodes, List<Edge> edges, int startNodeId) {
        List<Integer> traversalOrder = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        Set<Integer> visited = new HashSet<>();
        Map<Integer, Integer> parent = new HashMap<>();

        // Create adjacency list
        Map<Integer, List<Integer>> adjacencyList = buildAdjacencyList(nodes, edges);

        stack.push(startNodeId);

        while (!stack.isEmpty()) {
            int currentNodeId = stack.pop();

            if (!visited.contains(currentNodeId)) {
                visited.add(currentNodeId);
                traversalOrder.add(currentNodeId);

                // Get neighbors and push to stack (reverse order for DFS)
                List<Integer> neighbors = adjacencyList.getOrDefault(currentNodeId, new ArrayList<>());
                Collections.reverse(neighbors); // Reverse for correct DFS order

                for (int neighborId : neighbors) {
                    if (!visited.contains(neighborId)) {
                        stack.push(neighborId);
                        parent.put(neighborId, currentNodeId);
                    }
                }
            }
        }

        return traversalOrder;
    }

    private Map<Integer, List<Integer>> buildAdjacencyList(List<Node> nodes, List<Edge> edges) {
        Map<Integer, List<Integer>> adjacencyList = new HashMap<>();

        // Initialize adjacency list
        for (Node node : nodes) {
            adjacencyList.put(node.getId(), new ArrayList<>());
        }

        // Build adjacency list based on edge types
        for (Edge edge : edges) {
            if ("directed".equals(edge.getType())) {
                // For directed edges, only add one direction
                adjacencyList.get(edge.getSource()).add(edge.getTarget());
            } else {
                // For undirected edges, add both directions
                adjacencyList.get(edge.getSource()).add(edge.getTarget());
                adjacencyList.get(edge.getTarget()).add(edge.getSource());
            }
        }

        return adjacencyList;
    }

    public boolean hasPath(List<Node> nodes, List<Edge> edges, int startNodeId, int endNodeId) {
        List<Integer> traversalOrder = bfs(nodes, edges, startNodeId);
        return traversalOrder.contains(endNodeId);
    }

    public List<Integer> shortestPath(List<Node> nodes, List<Edge> edges, int startNodeId, int endNodeId) {
        // For unweighted graphs, BFS gives shortest path
        Map<Integer, Integer> parent = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();

        Map<Integer, List<Integer>> adjacencyList = buildAdjacencyList(nodes, edges);

        queue.add(startNodeId);
        visited.add(startNodeId);
        parent.put(startNodeId, null);

        while (!queue.isEmpty()) {
            int currentNodeId = queue.poll();

            if (currentNodeId == endNodeId) {
                // Reconstruct path
                return reconstructPath(parent, startNodeId, endNodeId);
            }

            List<Integer> neighbors = adjacencyList.getOrDefault(currentNodeId, new ArrayList<>());
            for (int neighborId : neighbors) {
                if (!visited.contains(neighborId)) {
                    visited.add(neighborId);
                    queue.add(neighborId);
                    parent.put(neighborId, currentNodeId);
                }
            }
        }

        return new ArrayList<>(); // No path found
    }

    private List<Integer> reconstructPath(Map<Integer, Integer> parent, int startNodeId, int endNodeId) {
        List<Integer> path = new ArrayList<>();
        Integer current = endNodeId;

        while (current != null) {
            path.add(0, current);
            current = parent.get(current);
        }

        if (path.get(0) == startNodeId) {
            return path;
        }

        return new ArrayList<>(); // Invalid path
    }
}
