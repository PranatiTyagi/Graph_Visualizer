package com.example.graphservice.model;

public class Edge {
    private int source;
    private int target;
    private String type; // "directed" or "undirected"
    private double weight;

    public Edge() {}

    public Edge(int source, int target, String type) {
        this.source = source;
        this.target = target;
        this.type = type;
        this.weight = 1.0; // default weight
    }

    public Edge(int source, int target, String type, double weight) {
        this.source = source;
        this.target = target;
        this.type = type;
        this.weight = weight;
    }

    public int getSource() { return source; }
    public void setSource(int source) { this.source = source; }

    public int getTarget() { return target; }
    public void setTarget(int target) { this.target = target; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
}
