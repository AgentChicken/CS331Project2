package edu.cpp.cs331.graphs.mstevens;

import edu.cpp.cs331.graphs.Vertex;

/**
 * Created by matthew on 3/12/17.
 */

/*
    * Here, a simple data structure that stores three values:
    * - vertex: A vertex
    * - weight: The total weight to get to that vertex
    * - nextHop: And the next vertex to visit to get back to the source
    * NextHop allows us to trace back to the original node to produce
    * the final shortest path
    * */
public class DijkstraRecord {
    private Vertex vertex;
    private int weight;
    private Vertex nextHop;

    public Vertex getVertex() {
        return vertex;
    }

    public int getWeight() {
        return weight;
    }

    public Vertex getNextHop() {
        return nextHop;
    }

    public DijkstraRecord(Vertex vertex, int weight, Vertex nextHop){
        this.vertex = vertex;
        this.weight = weight;
        this.nextHop = nextHop;
    }

    public String toString(){
        return "(" + vertex.toString() + ", " + weight + ", " + nextHop.toString() + ")";
    }
}