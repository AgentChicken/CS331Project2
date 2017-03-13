package edu.cpp.cs331.graphs.mstevens;

import edu.cpp.cs331.graphs.DirectedEdge;
import edu.cpp.cs331.graphs.Edge;
import edu.cpp.cs331.graphs.Graph;
import edu.cpp.cs331.graphs.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by matthew on 3/10/17.
 */

/*
* Used for testing the performance of the graphing and mapping
* algorithm. Nothing formal. I plan to remove it before submitting
* the project, but I'm keeping this here in case I forget or don't
* have time
* */
public class Tester {
    public static void main(String[] args) throws Graph.DuplicateEdgeException, Graph.VertexAlreadyExistsException {
        double graphSize = 4;
        Graph graph = Graph.genRandomUndirectedGraph((int) graphSize,1f);
        System.out.println(graph.toString());
        KruskalsMST kruskalsMST = new KruskalsMST();
        System.out.print(kruskalsMST.genMST(graph));
    }
}
