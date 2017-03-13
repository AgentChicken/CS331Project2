package edu.cpp.cs331.graphs.mstevens;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import edu.cpp.cs331.graphs.Edge;
import edu.cpp.cs331.graphs.Graph;
import edu.cpp.cs331.graphs.MinimumSpanningTree;
import edu.cpp.cs331.graphs.Vertex;

import java.util.*;

/**
 * Created by matthew on 3/12/17.
 */
public class KruskalsMST implements MinimumSpanningTree {
    @Override
    public Graph genMST(Graph g) {
        //List of edges which will be used to store the MST edges
        ArrayList<Edge> remainingEdges = new ArrayList<>();

        /*
        * Here, we initialize a SetThingy and initialize it
        * with sets, each containing a single vertex
        * */
        SetThingy setThingy = new SetThingy();
        for(Vertex vertex : g.getvList()){
            setThingy.createSet(vertex);
        }

        /*
        * Create a version of the edge list sorted by weight.
        * Special thanks to JetBrains. I didn't even know
        * comparingInt was a thing.
        * */
        List<Edge> sortedEdgeList = new ArrayList<>(g.geteList());
        sortedEdgeList.sort(Comparator.comparingInt(Edge::getWeight));

        /*
        * Here, we iterate through all the edges of the list
        * If the edges are in the same set, they're already
        * connected somehow, and we do nothing. If they aren't,
        * then we add the edge connecting them and unite their
        * respective sets. We know we're making the best choice
        * with regards to edges, as the list we're iterating
        * over is already sorted by weight
        * */
        for(Edge edge : sortedEdgeList){
            if(!setThingy.findSet(edge.getOne()).equals((setThingy.findSet(edge.getTwo())))){
                remainingEdges.add(edge);
                setThingy.unite(edge.getOne(), edge.getTwo());
            }
        }


        /*
        * Based on the list of edges, we create a new
        * graph representing the MST of the original.
        * We can add all the vertices, since an MST,
        * by definition, contains all the vertices of
        * the original graph. As for edges, we'll only
        * add the ones in remainingEdges, which stored
        * the edges we'd found in the last code block
        * */
        Graph result = new Graph();
        for(Vertex vertex : g.getvList()){
            try {
                result.addVertex(vertex);
            } catch (Graph.VertexAlreadyExistsException e) {
                e.printStackTrace();
            }
        }

        for(Edge edge : remainingEdges){
            try {
                result.addEdge(edge);
            } catch (Graph.InconsistentEdgeException | Graph.DuplicateEdgeException e) {
                e.printStackTrace();
            }
        }

        //return our MST!
        return result;
    }
}
